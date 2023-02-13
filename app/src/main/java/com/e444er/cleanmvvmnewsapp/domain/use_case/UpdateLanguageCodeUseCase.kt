package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageCode(languageTag = languageTag)
    }
}