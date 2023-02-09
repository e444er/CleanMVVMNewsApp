package com.e444er.cleanmvvmnewsapp.presentation.article

import com.e444er.cleanmvvmnewsapp.domain.model.Article

sealed class ArticleEvent(
    val article: Article
) {
    class SaveArticle(article: Article) : ArticleEvent(article)
    class DeleteArticle(article: Article) : ArticleEvent(article)
}
