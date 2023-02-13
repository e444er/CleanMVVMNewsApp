package com.e444er.cleanmvvmnewsapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmvvmnewsapp.domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase
) : ViewModel() {


    fun getUIMode(): Flow<Int> {
        return settingUseCase.getUIModeUseCase()
    }

    fun updateUIMode(uiMode: Int) {
        viewModelScope.launch {
            settingUseCase.updateUIModeUseCase(uiMode)
        }
    }

    fun updateLanguageIsoCode(languageTag: String) {
        viewModelScope.launch {
            settingUseCase.updateLanguageCodeUseCase(languageTag)
        }
    }

    fun getLanguageIsoCode(): Flow<String> {
        return settingUseCase.getLanguageCodeUseCase()
    }


}