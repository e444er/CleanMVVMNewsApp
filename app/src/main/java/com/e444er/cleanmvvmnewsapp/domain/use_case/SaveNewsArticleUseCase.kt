package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject


class SaveNewsArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article): Boolean {
        try {
            if (article.id == null) {
                newsRepository.saveOrUpdateNewsArticle(article)
            }
        } catch (e: java.lang.Exception) {
            return false
        }
        return true
    }
}