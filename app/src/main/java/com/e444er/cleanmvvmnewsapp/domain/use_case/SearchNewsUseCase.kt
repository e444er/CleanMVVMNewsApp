package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.common.Resource
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(search: String) : Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        try {
            val articles = newsRepository.searchNews(search).articles.map { it.toArticle() }
            emit(Resource.Success(articles))
        } catch (e: HttpException){
            emit(Resource.Error(errorMessage = e.localizedMessage ?: "Unknown Http Error"))
        } catch (e: IOException){
            emit(Resource.Error(errorMessage = "Server is not Available"))
        }
    }
}