package com.example.networkinteractions.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.networkinteractions.utils.dataStore
import com.example.networkinteractions.utils.themeSettingKeyFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    ) {

    suspend fun saveThemeSettingNews(theme: String){
        context.dataStore.edit {themeSetting ->
            themeSetting[themeSettingKeyFlow] = theme
        }
    }

    fun listenerCurrentTheme(): Flow<String> = context.dataStore.data
        .map {  theme ->
            theme[themeSettingKeyFlow] ?: ""
        }
}