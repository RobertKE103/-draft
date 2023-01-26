package com.example.networkinteractions.data.network

import com.example.networkinteractions.Utils.API_KEY
import com.example.networkinteractions.modules.NewsMain
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") key: String = API_KEY,
        @Query("q") category: String = "Android",
        @Query("searchIn") typeResponse: String = "title",
        @Query("sortBy") sort: String = "popularity"
    ): Response<NewsMain>

}