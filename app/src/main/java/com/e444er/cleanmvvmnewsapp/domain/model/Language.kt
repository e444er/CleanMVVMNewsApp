package com.e444er.cleanmvvmnewsapp.domain.model

import androidx.annotation.StringRes
import com.e444er.cleanmvvmnewsapp.R

data class Language(
    @StringRes val textId: Int,
    val iso: String
)

val supportedLanguages = mutableListOf<Language>(
    Language(R.string.language_russian, "ru"),
    Language(R.string.language_english, "us"),
    Language(R.string.language_turkish, "tr"),
    Language(R.string.language_german, "de"),
)
