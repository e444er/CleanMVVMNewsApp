package com.e444er.cleanmvvmnewsapp.data.local.dao

import androidx.room.*
import com.e444er.cleanmvvmnewsapp.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateNewsArticle(article: ArticleEntity)

    @Query("select * from ArticleEntity")
    fun getAllSavedNewsArticles(): Flow<List<ArticleEntity>>

    @Query("select * from ArticleEntity where url is :url and title is :title and publishedAt is :publishedAt")
    suspend fun findArticle(url: String, title: String, publishedAt: String): ArticleEntity

    @Delete
    suspend fun deleteNewsArticle(article: ArticleEntity)
}