package com.example.newsapptask.news_feature.data.repository

import com.example.newsapptask.news_feature.data.db.ArticleDao
import com.example.newsapptask.news_feature.data.remote.NewsApi
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNewsByCategory(category: String) =
        newsApi.getNewsByCategory(category = category)


    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

    override suspend fun getSearchNews(search: String, pageNumber: Int) =
        newsApi.searchForNews(search, pageNumber)

    override suspend fun upsertNews(article: Article) = articleDao.upsertNews(article)
    override fun getAllArticles(): Flow<List<Article>> =articleDao.getAllArticles()
    override suspend fun deleteNews(article: Article) =articleDao.deleteNews(article)

}