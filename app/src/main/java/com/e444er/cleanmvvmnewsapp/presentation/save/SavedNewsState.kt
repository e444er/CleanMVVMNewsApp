package com.e444er.cleanmvvmnewsapp.presentation.save

import com.e444er.cleanmvvmnewsapp.domain.model.Article

sealed class SavedNewsPageState {

    data class SavedNewsListState(
        val isLoading: Boolean = false,
        val articles: List<Article> = emptyList(),
    ) : SavedNewsPageState()

    data class SavedNewsActionState(
        val articleDeleted: Boolean = false,
        val articleDeletedUndo: Boolean = false
    ) : SavedNewsPageState()
}
