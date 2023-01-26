package com.example.networkinteractions.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsTable")
data class ArticleDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded(prefix = "prefix_")
    val source: SourceDb,
    val title: String,
    val url: String,
    val urlToImage: String
)