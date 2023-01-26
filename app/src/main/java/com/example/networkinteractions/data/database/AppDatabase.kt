package com.example.networkinteractions.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.networkinteractions.data.database.model.ArticleDb
import com.example.networkinteractions.data.database.model.ThemeDb

@Database(entities = [ArticleDb::class, ThemeDb::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsFavoriteDao(): NewsFavoriteDao
    abstract fun newsTheme(): NewsThemeDao
}