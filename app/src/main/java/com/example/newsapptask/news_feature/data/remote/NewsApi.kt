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
    @GET("v2/top-headlines") // Base URL
    suspend fun getNewsByCategory(
        @Query("country") country: String="us", // Country as a query parameter
        @Query("category") category: String, // Category as a query parameter
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,// Default API key
    ): Response<NewsResponse>
}