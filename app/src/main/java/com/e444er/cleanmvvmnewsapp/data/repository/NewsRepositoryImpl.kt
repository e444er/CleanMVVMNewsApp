package com.e444er.cleanmvvmnewsapp.data.repository

import com.e444er.cleanmvvmnewsapp.data.remote.api.NewsApi
import com.e444er.cleanmvvmnewsapp.data.remote.dto.NewsResponseDto
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
//    private val newsArticleDao: NewsArticleDao
) : NewsRepository {
    override suspend fun getTopHeadLines(): NewsResponseDto {
        return newsApi.getTopHeadlines()
    }

    override suspend fun searchNews(search: String): NewsResponseDto {
        return newsApi.searchNews(search)
    }

//    override suspend fun saveOrUpdateNewsArticle(article: ArticleEntity) {
//        return newsArticleDao.saveOrUpdateNewsArticle(article)
//    }
//
//    override fun getAllSavedNewsArticles(): Flow<List<ArticleEntity>> {
//        return newsArticleDao.getAllSavedNewsArticles()
//    }
//
//    override suspend fun findArticle(url: String, title: String, publishedAt: String): ArticleEntity {
//        return newsArticleDao.findArticle(url,title,publishedAt)
//    }
//
//    override suspend fun deleteNewsArticle(article: ArticleEntity) {
//        return newsArticleDao.deleteNewsArticle(article)
//    }

}