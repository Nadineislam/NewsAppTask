package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class UpsertNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(article: Article) = newsRepository.upsertNews(article)
}