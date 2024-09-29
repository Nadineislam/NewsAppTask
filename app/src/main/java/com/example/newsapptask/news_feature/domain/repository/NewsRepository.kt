package com.example.newsapptask.news_feature.domain.repository

import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Response<NewsResponse>

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

    suspend fun getSearchNews(search: String, pageNumber: Int): Response<NewsResponse>

    suspend fun upsertNews(article: Article)

    fun getAllArticles(): Flow<List<Article>>

    suspend fun deleteNews(article: Article)
}