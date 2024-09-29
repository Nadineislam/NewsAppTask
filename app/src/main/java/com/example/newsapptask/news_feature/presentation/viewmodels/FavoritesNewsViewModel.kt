package com.example.newsapptask.news_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesNewsViewModel @Inject constructor(
   private val newsRepository: NewsRepository
) : ViewModel() {

    private val _favoriteArticles = MutableStateFlow<List<Article>>(emptyList())
    val favoriteArticles = _favoriteArticles.asStateFlow()

    init {
        getFavoriteArticles()
    }

    private fun getFavoriteArticles() = viewModelScope.launch {
        newsRepository.getFavoriteArticles()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )
            .collect { articles ->
                _favoriteArticles.emit(articles)
            }
    }

    fun deleteFavoriteNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteFavoriteNews(article)
    }

}