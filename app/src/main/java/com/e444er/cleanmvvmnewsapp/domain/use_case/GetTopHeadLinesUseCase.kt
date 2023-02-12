package com.e444er.cleanmvvmnewsapp.domain.use_case

import androidx.paging.PagingData
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadLinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return newsRepository.getTopHeadLines()
    }
}