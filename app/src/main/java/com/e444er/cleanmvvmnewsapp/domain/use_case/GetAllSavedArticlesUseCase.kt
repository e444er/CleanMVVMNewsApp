package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllSavedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getAllSavedNewsArticles().map {
        it.map { articleEntity ->
            articleEntity.toArticle()
        }
    }
}




