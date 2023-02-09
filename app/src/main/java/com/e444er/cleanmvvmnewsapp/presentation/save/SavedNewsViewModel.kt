package com.e444er.cleanmvvmnewsapp.presentation.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmvvmnewsapp.domain.use_case.DeleteNewsArticleUseCase
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetAllSavedArticlesUseCase
import com.e444er.cleanmvvmnewsapp.domain.use_case.SaveNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val getAllSavedArticlesUseCase: GetAllSavedArticlesUseCase,
    private val deleteNewsArticleUseCase: DeleteNewsArticleUseCase,
    private val saveNewsArticleUseCase: SaveNewsArticleUseCase
) : ViewModel() {

    private var _savedNewsState: MutableLiveData<SavedNewsPageState> = MutableLiveData()
    val savedNewsState: LiveData<SavedNewsPageState>
        get() = _savedNewsState

    init {
        getAllSavedNews()
    }

    private fun getAllSavedNews() = viewModelScope.launch {
        _savedNewsState.value = SavedNewsPageState.SavedNewsListState(true)
        getAllSavedArticlesUseCase().collect {
            _savedNewsState.value = SavedNewsPageState.SavedNewsListState(false, it)
        }
    }

    fun onEvent(event: SavedNewsEvent) {
        viewModelScope.launch {
            when (event) {
                is SavedNewsEvent.DeleteArticle -> {
                    deleteNewsArticleUseCase(event.article)
                    _savedNewsState.value = SavedNewsPageState.SavedNewsActionState(articleDeleted = true)
                }
                is SavedNewsEvent.UndoDeleteArticle -> {
                    saveNewsArticleUseCase(event.article)
                    _savedNewsState.value =
                        SavedNewsPageState.SavedNewsActionState(articleDeletedUndo = true)
                }
            }
        }
    }
}