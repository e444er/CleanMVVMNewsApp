package com.e444er.cleanmvvmnewsapp.presentation.topNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmvvmnewsapp.common.Resource
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetTopHeadLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase
) : ViewModel() {

    private var _state: MutableLiveData<TopState> = MutableLiveData()

    val state: LiveData<TopState>
        get() = _state

    init {
        getTopHeadLines()
    }

    private fun getTopHeadLines() = viewModelScope.launch {
        getTopHeadLinesUseCase().collect { result ->
            when (result) {
                is Resource.Error -> _state.value =
                    TopState(errorMessage = result.errorMessage ?: "Unknown Error")
                is Resource.Loading -> _state.value = TopState(true)
                is Resource.Success -> _state.value = TopState(data = result.data ?: emptyList())
            }
        }
    }
}