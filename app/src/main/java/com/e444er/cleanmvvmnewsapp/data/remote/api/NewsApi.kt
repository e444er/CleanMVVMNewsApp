package com.e444er.cleanmvvmnewsapp.data.remote.api

import com.e444er.cleanmvvmnewsapp.data.remote.dto.NewsResponseDto
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "ru",
        @Query("category") category: String = "science",
        @Query("pageSize") pageSize: Int = 30,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponseDto

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") search: String,
        @Query("language") language: String = "ru",
        @Query("pageSize") pageSize: Int = 30,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponseDto

}