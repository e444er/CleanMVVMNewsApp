package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.data.local.entity.ArticleEntity
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.model.toArticleEntity
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject


class SaveNewsArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article): Boolean {
        val articleEntity : ArticleEntity? =
            newsRepository.findArticle(article.url, article.title!!, article.publishedAt!!)
        try {
            if (articleEntity?.id == null) {
                newsRepository.saveOrUpdateNewsArticle(article.toArticleEntity())
            }
        } catch (e: java.lang.Exception) {
            return false
        }
        return true
    }
}