package com.e444er.cleanmvvmnewsapp.domain.repository

import com.e444er.cleanmvvmnewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadLines(): List<Article>

    suspend fun searchNews(search: String): List<Article>

    suspend fun saveOrUpdateNewsArticle(article: Article)

    fun getAllSavedNewsArticles(): Flow<List<Article>>

//    suspend fun findArticle(url: String, title: String, publishedAt: String): Article?

    suspend fun deleteNewsArticle(article: Article)
}