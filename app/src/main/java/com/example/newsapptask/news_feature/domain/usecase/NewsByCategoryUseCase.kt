package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class NewsByCategoryUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String) =
        newsRepository.getNewsByCategory(category)

}