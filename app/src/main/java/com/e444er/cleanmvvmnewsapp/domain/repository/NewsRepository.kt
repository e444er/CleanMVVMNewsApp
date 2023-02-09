package com.e444er.cleanmvvmnewsapp.domain.repository

import com.e444er.cleanmvvmnewsapp.data.remote.dto.NewsResponseDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadLines() : NewsResponseDto

    suspend fun searchNews(search: String) : NewsResponseDto

//    suspend fun saveOrUpdateNewsArticle(article: ArticleEntity)
//
//    fun getAllSavedNewsArticles() : Flow<List<ArticleEntity>>
//
//    suspend fun findArticle(url: String, title: String, publishedAt: String): ArticleEntity?
//
//    suspend fun deleteNewsArticle(article: ArticleEntity)
}