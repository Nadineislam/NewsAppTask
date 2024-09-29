package com.example.newsapptask.news_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    fun upsertFavoriteNews(article: Article) = viewModelScope.launch {
        newsRepository.upsertFavoriteNews(article)
    }
}