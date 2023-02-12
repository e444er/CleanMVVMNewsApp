package com.e444er.cleanmvvmnewsapp.presentation.searchNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmvvmnewsapp.common.Resource
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.use_case.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {
//
    private val _searchState = MutableStateFlow(SearchNewsState())
    val searchNewsState: StateFlow<SearchNewsState> = _searchState.asStateFlow()

//    fun getTopHeadLines(search: String): Flow<PagingData<Article>> {
//        return searchNewsUseCase(search).cachedIn(viewModelScope)
//    }

    suspend fun searchNews(search: String) {
        searchNewsUseCase(search).collect { resulat ->
            when (resulat) {
                is Resource.Error -> _searchState.value =
                    SearchNewsState(errorMessage = resulat.errorMessage ?: "Unknown error")
                is Resource.Loading -> _searchState.value = SearchNewsState(true)
                is Resource.Success -> _searchState.value =
                    SearchNewsState(articles = resulat.data ?: emptyList())
            }
        }
    }
}