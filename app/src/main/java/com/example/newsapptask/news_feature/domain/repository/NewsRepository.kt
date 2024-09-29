package com.example.newsapptask.news_feature.domain.repository

import com.example.newsapptask.core.utils.Resource
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Resource<NewsResponse>

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Resource<NewsResponse>

    suspend fun getSearchNews(search: String, pageNumber: Int): Response<NewsResponse>

    fun getArticlesByCategory(category: String): Flow<List<Article>>

    suspend fun upsertNews(article: List<Article>)

    fun getAllArticles(): Flow<List<Article>>

    suspend fun deleteNews(article: Article)

    fun getFavoriteArticles(): Flow<List<Article>>
    suspend fun deleteFavoriteNews(article: Article)

    suspend fun upsertFavoriteNews(article: Article)
}