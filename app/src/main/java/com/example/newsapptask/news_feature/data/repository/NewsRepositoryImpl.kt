package com.example.newsapptask.news_feature.data.repository

import com.example.newsapptask.news_feature.data.remote.NewsApi
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNewsByCategory(category: String) =
        newsApi.getNewsByCategory(category = category)


    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

    override suspend fun getSearchNews(search: String, pageNumber: Int) =
        newsApi.searchForNews(search, pageNumber)

}