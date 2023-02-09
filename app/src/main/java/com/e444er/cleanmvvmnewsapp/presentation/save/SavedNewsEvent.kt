package com.e444er.cleanmvvmnewsapp.presentation.save

import com.e444er.cleanmvvmnewsapp.domain.model.Article

sealed class SavedNewsEvent {
    data class DeleteArticle(val article: Article) : SavedNewsEvent()
    data class UndoDeleteArticle(val article: Article) : SavedNewsEvent()
}