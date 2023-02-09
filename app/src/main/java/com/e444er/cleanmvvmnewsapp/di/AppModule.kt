package com.e444er.cleanmvvmnewsapp.di

import android.content.Context
import androidx.room.Room
import com.e444er.cleanmvvmnewsapp.data.local.Converters
import com.e444er.cleanmvvmnewsapp.data.local.NewsArticleDatabase
import com.e444er.cleanmvvmnewsapp.data.local.dao.NewsArticleDao
import com.e444er.cleanmvvmnewsapp.data.remote.api.NewsApi
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.BASE_URL
import com.e444er.cleanmvvmnewsapp.data.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun baseUrl() = BASE_URL

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)


    @Provides
    @Singleton
    fun provideNewsArticleDatabase(@ApplicationContext context: Context): NewsArticleDatabase =
        Room.databaseBuilder(
            context,
            NewsArticleDatabase::class.java,
            NewsArticleDatabase.DATABASE_NAME
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()

    @Provides
    @Singleton
    fun provideNewsArticleDao(newsArticleDatabase: NewsArticleDatabase): NewsArticleDao =
        newsArticleDatabase.getNewsArticleDao()
}