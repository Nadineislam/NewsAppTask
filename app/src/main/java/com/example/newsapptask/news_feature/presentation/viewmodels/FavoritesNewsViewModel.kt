package com.example.newsapptask.news_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.domain.usecase.ArticlesUseCase
import com.example.newsapptask.news_feature.domain.usecase.DeleteNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesNewsViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : ViewModel() {
    private val _allArticles = MutableStateFlow<List<Article>>(emptyList())
    val allArticles = _allArticles.asStateFlow()

    init {
        getAllArticles()
    }

    private fun getAllArticles() = viewModelScope.launch {
        articlesUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )
            .collect { articles ->
                _allArticles.emit(articles)
            }

    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        deleteNewsUseCase(article)
    }
}