package com.e444er.cleanmvvmnewsapp.presentation.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmvvmnewsapp.domain.use_case.DeleteNewsArticleUseCase
import com.e444er.cleanmvvmnewsapp.domain.use_case.SaveNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val savedArticlesUseCase: SaveNewsArticleUseCase,
    private val deleteNewsArticleUseCase: DeleteNewsArticleUseCase
) : ViewModel() {

    private var _articleState: MutableLiveData<ArticleState> = MutableLiveData()

    val articleState: LiveData<ArticleState>
        get() = _articleState


    fun onEvent(event: ArticleEvent) = viewModelScope.launch {
        when (event) {
            is ArticleEvent.SaveArticle -> {
                val saveResult = savedArticlesUseCase(event.article)
                if (saveResult){
                    _articleState.value = ArticleState(true)
                }
            }
            is ArticleEvent.DeleteArticle -> {
             deleteNewsArticleUseCase(event.article)
                _articleState.value = ArticleState(articleSaveUndo = true)
            }
        }
    }

}