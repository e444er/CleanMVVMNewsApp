package com.e444er.cleanmvvmnewsapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.e444er.cleanmvvmnewsapp.data.repository.DataOperationsImpl
import com.e444er.cleanmvvmnewsapp.data.utils.Constants
import com.e444er.cleanmvvmnewsapp.domain.repository.DataStoreOperations
import com.e444er.cleanmvvmnewsapp.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(Constants.PREFERENCES_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        dataStore: DataStore<Preferences>
    ): DataStoreOperations {
        return DataOperationsImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        dataStoreOperations: DataStoreOperations
    ): SettingUseCase {
        return SettingUseCase(
            getUIModeUseCase = GetUIModeUseCase(dataStoreOperations),
            updateUIModeUseCase = UpdateUIModeUseCase(dataStoreOperations),
            updateLanguageCodeUseCase = UpdateLanguageCodeUseCase(dataStoreOperations),
            getLanguageCodeUseCase = GetLanguageCodeUseCase(dataStoreOperations)
        )
    }

}