package com.e444er.cleanmvvmnewsapp.presentation.searchNews

import com.e444er.cleanmvvmnewsapp.domain.model.Article

data class SearchNewsState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val errorMessage: String = ""
)
