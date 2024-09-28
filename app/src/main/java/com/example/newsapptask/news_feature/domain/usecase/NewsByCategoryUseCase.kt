package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsByCategoryUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String): Response<NewsResponse> =
        newsRepository.getNewsByCategory(category)

}