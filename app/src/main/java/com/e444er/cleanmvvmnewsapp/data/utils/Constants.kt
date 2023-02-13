package com.e444er.cleanmvvmnewsapp.data.utils

import com.e444er.cleanmvvmnewsapp.BuildConfig

class Constants {
    companion object{
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = BuildConfig.BASE_URL
        const val STARTING_PAGE = 1
        const val ITEMS_PER_PAGE = 20
        const val LOCALE_KEY = "locale_key"

        const val UI_MODE_KEY = "ui_mode_key"
        const val DEFAULT_REGION = "RU"
        const val PREFERENCES_NAME = "mova_preferences_name"
        const val DEFAULT_LANGUAGE = "ru"
    }
}