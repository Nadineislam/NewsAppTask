package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class ArticlesUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke() = newsRepository.getAllArticles()
}