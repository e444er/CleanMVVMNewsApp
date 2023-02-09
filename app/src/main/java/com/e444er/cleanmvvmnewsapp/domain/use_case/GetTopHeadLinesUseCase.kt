package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.common.Resource
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopHeadLinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() : Flow<Resource<List<Article>>> = flow {

        emit(Resource.Loading())

        try {
            val articleDTOs = newsRepository.getTopHeadLines().articles
            emit(Resource.Success(articleDTOs.map{it.toArticle()}))
        } catch (e: HttpException){
            emit(Resource.Error(errorMessage = e.localizedMessage ?: "Unknown Http Connection Error"))
        } catch (e : IOException) {
            emit(Resource.Error(errorMessage = e.localizedMessage ?: "Server is not available"))
        }

    }

}