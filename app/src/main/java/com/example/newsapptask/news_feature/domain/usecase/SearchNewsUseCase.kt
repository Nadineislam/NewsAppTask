package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(search: String, pageNumber: Int): Response<NewsResponse> =
        newsRepository.getSearchNews(search, pageNumber)
}