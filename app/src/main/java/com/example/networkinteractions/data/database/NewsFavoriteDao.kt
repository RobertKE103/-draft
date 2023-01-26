package com.example.networkinteractions.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.networkinteractions.data.database.model.ArticleDb

@Dao
interface NewsFavoriteDao {

    @Query("SELECT * FROM NewsTable")
    suspend fun getAllNews(): List<ArticleDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ArticleDb::class)
    suspend fun insertNews(articleDb: ArticleDb)

    @Query("DELETE FROM NewsTable WHERE id=:newsId")
    suspend fun deleteNewsFavorite(newsId: Int)
}