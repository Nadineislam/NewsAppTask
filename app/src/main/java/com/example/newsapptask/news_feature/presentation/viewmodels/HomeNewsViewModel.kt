package com.example.newsapptask.news_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.core.utils.Resource
import com.example.newsapptask.core.utils.handleResponse
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.usecase.BreakingNewsUseCase
import com.example.newsapptask.news_feature.domain.usecase.NewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeNewsViewModel @Inject constructor(
    private val breakingNewsUseCase: BreakingNewsUseCase,
    private val newsByCategoryUseCase: NewsByCategoryUseCase
) : ViewModel() {
    private val _breakingNews: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val breakingNews = _breakingNews.asStateFlow()

    private val _newsByCategory: MutableStateFlow<Resource<NewsResponse>> =
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
        val response = breakingNewsUseCase(countryCode, breakingNewsPage)
        _breakingNews.emit(handleBreakingNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
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
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getNewsByCategory(category: String) = viewModelScope.launch {
        _selectedCategory.emit(category)
        val response = newsByCategoryUseCase(category)
        _newsByCategory.emit(handleResponse(response))
    }

}
