package com.example.networkinteractions.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_key")
val themeSettingKeyFlow = stringPreferencesKey("theme_key")
const val THEME_KEY = "theme_key"

