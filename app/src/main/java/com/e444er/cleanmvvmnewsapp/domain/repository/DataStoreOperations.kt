package com.e444er.cleanmvvmnewsapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun updateCurrentLanguageCode(
        languageTag: String
    )

    fun getLanguageCode(): Flow<String>

    suspend fun updateUIMode(uiMode: Int)

    fun getUIMode(): Flow<Int>
}