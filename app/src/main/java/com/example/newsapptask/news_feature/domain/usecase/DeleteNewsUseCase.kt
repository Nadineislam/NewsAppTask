package com.example.newsapptask.news_feature.domain.usecase

import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(article: Article) = newsRepository.deleteNews(article)
}