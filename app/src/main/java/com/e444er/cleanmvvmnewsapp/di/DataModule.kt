package com.e444er.cleanmvvmnewsapp.di

import com.e444er.cleanmvvmnewsapp.data.repository.NewsRepositoryImpl
import com.e444er.cleanmvvmnewsapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindNewsRepository(impl: NewsRepositoryImpl) : NewsRepository
}