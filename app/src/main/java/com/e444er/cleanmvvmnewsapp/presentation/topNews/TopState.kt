package com.e444er.cleanmvvmnewsapp.presentation.topNews

import com.e444er.cleanmvvmnewsapp.domain.model.Article

data class TopState(
    val isLoading : Boolean = false,
    val data : List<Article> = emptyList(),
    val errorMessage : String = ""
)
