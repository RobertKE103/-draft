package com.example.networkinteractions.domain

import androidx.paging.PagingSource
import com.example.networkinteractions.data.NewsRepository
import com.example.networkinteractions.modules.Article
import javax.inject.Inject

class NewsEverythingUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(theme: String): PagingSource<Int, Article>{
        return repository.getNewsResult(theme)
    }
}