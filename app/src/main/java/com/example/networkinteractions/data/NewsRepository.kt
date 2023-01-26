package com.example.networkinteractions.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.networkinteractions.data.database.NewsThemeDao
import com.example.networkinteractions.data.network.EverythingNewsPageSource
import com.example.networkinteractions.data.network.EverythingNewsPageSource_Factory
import com.example.networkinteractions.data.network.NewsService
import com.example.networkinteractions.modules.Article
import com.example.networkinteractions.utils.dataStore
import com.example.networkinteractions.utils.themeSettingKeyFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val themeDao: NewsThemeDao,
    @ApplicationContext private val context: Context,
    private val everythingnewspagesourceFactory: EverythingNewsPageSource.Factory
) {

    fun getNewsResult(theme: String = "Android"): PagingSource<Int, Article> =
        everythingnewspagesourceFactory.create(theme)



    suspend fun saveThemeSettingNews(theme: String) {
        context.dataStore.edit { themeSetting ->
            themeSetting[themeSettingKeyFlow] = theme
        }
    }


}