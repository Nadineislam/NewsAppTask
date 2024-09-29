package com.example.newsapptask.news_feature.presentation.viewmodels

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.core.utils.Resource
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.repository.NewsRepository
import com.example.newsapptask.news_feature.domain.usecase.ArticlesUseCase
import com.example.newsapptask.news_feature.domain.usecase.BreakingNewsUseCase
import com.example.newsapptask.news_feature.domain.usecase.NewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNewsViewModel @Inject constructor(
    private val breakingNewsUseCase: BreakingNewsUseCase,
    private val newsByCategoryUseCase: NewsByCategoryUseCase,
    private val newsRepository: NewsRepository,
    private val articlesUseCase: ArticlesUseCase,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {
    private val _breakingNews: MutableStateFlow<Resource<List<Article>>> =
        MutableStateFlow(Resource.Loading())
    val breakingNews = _breakingNews.asStateFlow()

    private val _newsByCategory: MutableStateFlow<Resource<List<Article>>> =
        MutableStateFlow(Resource.Loading())
    val newsByCategory = _newsByCategory.asStateFlow()

    private val _selectedCategory = MutableStateFlow("business")
    val selectedCategory = _selectedCategory.asStateFlow()
    private var breakingNewsPage = 1
    private var breakingNewsResponse: NewsResponse? = null


    val categories = listOf("business", "health", "technology")


    init {
        getBreakingNews("us")
        getNewsByCategory(_selectedCategory.value)
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        if (isInternetAvailable()) {
            when (val resource = breakingNewsUseCase(countryCode, breakingNewsPage)) {
                is Resource.Success -> {
                    resource.data?.let { resultResponse ->
                        breakingNewsPage++
                        if (breakingNewsResponse == null) {
                            breakingNewsResponse = resultResponse
                        } else {
                            val existingUrls = breakingNewsResponse?.articles?.map { it.url }
                            val newArticles = resultResponse.articles.filterNot { newArticle ->
                                existingUrls?.contains(newArticle.url) == true
                            }
                            breakingNewsResponse?.articles?.addAll(newArticles)
                        }
                        _breakingNews.emit(
                            Resource.Success(
                                breakingNewsResponse?.articles ?: emptyList()
                            )
                        )
                    }
                }
                is Resource.Error -> {
                    _breakingNews.emit(Resource.Error(resource.message ?: "Unknown error"))
                }
                is Resource.Loading -> {
                    _breakingNews.emit(Resource.Loading())
                }
            }
        } else {
            val cachedArticles = articlesUseCase().first()
            if (cachedArticles.isNotEmpty()) {
                _breakingNews.emit(Resource.Success(cachedArticles))
            } else {
                _breakingNews.emit(Resource.Error("No internet and no cached articles"))
            }
        }
    }


    private fun isInternetAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }


    fun getNewsByCategory(category: String) = viewModelScope.launch {
        _selectedCategory.emit(category)

        if (isInternetAvailable()) {
            when (val response = newsByCategoryUseCase(category)) {
                is Resource.Success -> {
                    response.data?.let { resultResponse ->
                        val articlesWithCategory = resultResponse.articles.map { article ->
                            article.copy(category = category)
                        }
                        newsRepository.upsertNews(articlesWithCategory)
                        _newsByCategory.emit(Resource.Success(articlesWithCategory))
                    }
                }
                is Resource.Error -> {
                    _newsByCategory.emit(Resource.Error(response.message ?: "Unknown error"))
                }
                is Resource.Loading -> {
                    _newsByCategory.emit(Resource.Loading())
                }
            }
        } else {
            val cachedArticles = newsRepository.getArticlesByCategory(category).first()
            if (cachedArticles.isNotEmpty()) {
                _newsByCategory.emit(Resource.Success(cachedArticles))
            } else {
                _newsByCategory.emit(Resource.Error("No internet and no cached articles"))
            }
        }
    }

}



