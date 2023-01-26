package com.example.networkinteractions.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme")
data class ThemeDb(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val theme: String
)
