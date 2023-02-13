package com.e444er.cleanmvvmnewsapp.domain.use_case

import com.e444er.cleanmvvmnewsapp.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    suspend operator fun invoke(uiMode: Int) {
        dataStoreOperations.updateUIMode(uiMode)
    }
}