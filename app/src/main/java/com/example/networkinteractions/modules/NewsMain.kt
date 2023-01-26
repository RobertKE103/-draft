package com.example.networkinteractions.modules

data class NewsMain(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)