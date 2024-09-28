package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class BreakingNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(countryCode: String, pageNumber: Int): Response<NewsResponse> =
        newsRepository.getBreakingNews(countryCode, pageNumber)

}