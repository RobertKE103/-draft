package com.example.networkinteractions.data.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.networkinteractions.data.database.model.ArticleDb
import javax.inject.Inject

class DatabasePageSource @Inject constructor(
    private val newsDao: NewsFavoriteDao
): PagingSource<Int, ArticleDb>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDb>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDb> {
        val page = params.key ?: 1 // номер страницы
        val pageSize = params.loadSize // сколько элментов запрашивать
        return try {
            val favoriteList = newsDao.getAllNews()
            return run {
                val nextKey = if (favoriteList.size == pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(favoriteList, prevKey, nextKey)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}