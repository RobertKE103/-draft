package com.example.networkinteractions.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.networkinteractions.modules.Article
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import javax.inject.Inject

class EverythingNewsPageSource @AssistedInject constructor(
    private val newsService: NewsService,
    @Assisted("theme") private val theme: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            val response = newsService.getNews(category = theme)
            Log.d("dataStore", "${response.body()?.articles}")
            return if (response.isSuccessful) {
                val articles = checkNotNull(response.body()?.articles)
                val nextKey = if (articles.size == pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(articles, prevKey, nextKey)
            } else {
                Log.d("dataStore", "error EVPS in try")
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Log.d("dataStore", "error EVPS in catch")
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("theme") theme: String): EverythingNewsPageSource
    }


}