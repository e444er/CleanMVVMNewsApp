package com.e444er.cleanmvvmnewsapp.domain.use_case

data class SettingUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
    val updateLanguageCodeUseCase: UpdateLanguageCodeUseCase,
    val getLanguageCodeUseCase: GetLanguageCodeUseCase
)