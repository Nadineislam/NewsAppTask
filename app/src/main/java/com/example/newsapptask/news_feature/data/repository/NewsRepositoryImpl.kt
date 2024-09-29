package com.example.newsapptask.news_feature.data.repository

import com.example.newsapptask.core.utils.Resource
import com.example.newsapptask.news_feature.data.db.ArticleDao
import com.example.newsapptask.news_feature.data.remote.NewsApi
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNewsByCategory(category: String): Resource<NewsResponse> {
        return try {
            val response = newsApi.getNewsByCategory(category = category)
            if (response.isSuccessful) {
                response.body()?.let {
                    val articlesWithCategory = it.articles.map { article ->
                        article.copy(category = category, isBreakingNews = false)
                    }
                    articleDao.upsertNews(articlesWithCategory)
                    Resource.Success(it)
                } ?: Resource.Error("No data found")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Resource<NewsResponse> {
        return try {
            val response = newsApi.getBreakingNews(countryCode, pageNumber)
            if (response.isSuccessful) {
                response.body()?.let {
                    val articlesAsBreakingNews = it.articles.map { article ->
                        article.copy(isBreakingNews = true, category = null)
                    }
                    articleDao.upsertNews(articlesAsBreakingNews)
                    Resource.Success(it)
                } ?: Resource.Error("No data found")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }


    override suspend fun getSearchNews(search: String, pageNumber: Int) =
        newsApi.searchForNews(search, pageNumber)

    override suspend fun upsertNews(article: List<Article>) = articleDao.upsertNews(article)
    override fun getAllArticles(): Flow<List<Article>> =articleDao.getAllArticles()
    override suspend fun deleteNews(article: Article) =articleDao.deleteNews(article)
    override fun getFavoriteArticles(): Flow<List<Article>> = articleDao.getFavoriteArticles()
    override fun getArticlesByCategory(category: String) = articleDao.getArticlesByCategory(category)

    override suspend fun deleteFavoriteNews(article: Article) {
        articleDao.deleteNews(article.copy(favorite = false))
    }

    override suspend fun upsertFavoriteNews(article: Article) {
        articleDao.upsertNews(listOf(article.copy(favorite = true)))

    }

}