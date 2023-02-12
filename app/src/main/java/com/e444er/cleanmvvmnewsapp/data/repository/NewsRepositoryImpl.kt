package com.e444er.cleanmvvmnewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmvvmnewsapp.data.local.dao.NewsArticleDao
import com.e444er.cleanmvvmnewsapp.data.local.entity.toArticleEntity
import com.e444er.cleanmvvmnewsapp.data.paging_source.NewsPaging
import com.e444er.cleanmvvmnewsapp.data.paging_source.SearchPaging
import com.e444er.cleanmvvmnewsapp.data.remote.api.NewsApi
import com.e444er.cleanmvvmnewsapp.data.remote.dto.toMovieList
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.ITEMS_PER_PAGE
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDao: NewsArticleDao
) : NewsRepository {
    override fun getTopHeadLines(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                NewsPaging(
                    newsApi = newsApi
                )
            }
        ).flow
    }

    override suspend fun searchNews(search: String): List<Article> {
        return newsApi.searchNews(search).articles.toMovieList()
    }

//    override fun searchNews(search: String): Flow<PagingData<Article>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = ITEMS_PER_PAGE
//            ),
//            pagingSourceFactory = {
//                SearchPaging(
//                    newsApi = newsApi,
//                    search = search
//                )
//            }
//        ).flow
//    }

    override suspend fun saveOrUpdateNewsArticle(article: Article) {
        return newsArticleDao.saveOrUpdateNewsArticle(article.toArticleEntity())
    }

    override fun getAllSavedNewsArticles(): Flow<List<Article>> {
        return newsArticleDao.getAllSavedNewsArticles().map {
            it.map { articleEntity ->
                articleEntity.toArticle()
            }
        }
    }

    override suspend fun deleteNewsArticle(article: Article) {
        return newsArticleDao.deleteNewsArticle(article.toArticleEntity())
    }

}