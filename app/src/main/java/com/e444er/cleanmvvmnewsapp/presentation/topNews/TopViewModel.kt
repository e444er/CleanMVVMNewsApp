package com.e444er.cleanmvvmnewsapp.presentation.topNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetTopHeadLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase
) : ViewModel() {


    fun getTopHeadLines(): Flow<PagingData<Article>> {
        return getTopHeadLinesUseCase().cachedIn(viewModelScope)
    }
}