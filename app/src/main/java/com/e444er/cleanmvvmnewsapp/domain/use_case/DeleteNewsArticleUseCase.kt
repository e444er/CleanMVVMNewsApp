package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.model.toArticleEntity
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject


class DeleteNewsArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteNewsArticle(article.toArticleEntity())
    }
}