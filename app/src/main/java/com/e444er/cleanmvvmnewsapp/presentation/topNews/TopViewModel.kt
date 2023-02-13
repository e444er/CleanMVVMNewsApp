package com.e444er.cleanmvvmnewsapp.presentation.topNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetTopHeadLinesUseCase
import com.e444er.cleanmvvmnewsapp.domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase,
    private val settingUseCase: SettingUseCase,
) : ViewModel() {

    private val _languageIsoCode = MutableStateFlow("")
    val languageIsoCode: StateFlow<String> get() = _languageIsoCode

    fun getTopHeadLines(): Flow<PagingData<Article>> {
        return getTopHeadLinesUseCase(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getLanguageIsoCode(): Flow<String> {
        return settingUseCase.getLanguageCodeUseCase()
    }

    fun setLanguageIsoCode(languageIsoCode: String) {
        _languageIsoCode.value = languageIsoCode
        setLanguageIsoCodeInDataStore(languageIsoCode)
    }

    private fun setLanguageIsoCodeInDataStore(languageIsoCode: String) {
        viewModelScope.launch {
            settingUseCase.updateLanguageCodeUseCase(languageIsoCode)
        }
    }
}