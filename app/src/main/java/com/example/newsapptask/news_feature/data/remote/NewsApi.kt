package com.example.newsapptask.news_feature.data.remote

import com.example.newsapptask.BuildConfig
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>
    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("country") country: String="us",
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>
}