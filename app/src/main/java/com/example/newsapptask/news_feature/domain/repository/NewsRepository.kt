package com.example.newsapptask.news_feature.domain.repository

import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Response<NewsResponse>

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>
}