package com.example.networkinteractions.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkinteractions.data.dataStore.ThemeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themeRepositoryImpl: ThemeRepositoryImpl,
) : ViewModel() {

    fun saveThemeSetting(theme: String) {
        viewModelScope.launch {
            themeRepositoryImpl.saveThemeSettingNews(theme)
        }
    }

    val themeSettingFlow: Flow<String> = themeRepositoryImpl.listenerCurrentTheme()

}