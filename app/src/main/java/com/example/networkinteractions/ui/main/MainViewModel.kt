package com.example.networkinteractions.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.networkinteractions.data.NewsRepository
import com.example.networkinteractions.data.dataStore.ThemeRepositoryImpl
import com.example.networkinteractions.data.network.EverythingNewsPageSource
import com.example.networkinteractions.data.network.NewsService
import com.example.networkinteractions.domain.NewsEverythingUseCase
import com.example.networkinteractions.modules.Article
import com.example.networkinteractions.utils.THEME_KEY
import com.example.networkinteractions.utils.dataStore
import com.example.networkinteractions.utils.themeSettingKeyFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val newsEverythingUseCase: Provider<NewsEverythingUseCase>,
    private val themeRepositoryImpl: ThemeRepositoryImpl
) : ViewModel() {

    private val theme = themeRepositoryImpl.listenerCurrentTheme()

    private val _isVisibleEditText = MutableStateFlow(true)
    val isVisibleEditText = _isVisibleEditText.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val news: StateFlow<PagingData<Article>> = theme
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setupVisibleET(isVisibility: Boolean){
        _isVisibleEditText.value = isVisibility
    }

    private fun newPager(theme: String): Pager<Int, Article>{
        return Pager(PagingConfig(pageSize = 5, enablePlaceholders = false)){
            val newsEverythingUseCase = newsEverythingUseCase.get()
            newsEverythingUseCase(theme).also { newPagingSource = it }
        }
    }
}