package com.example.newsapptask.news_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.core.utils.Resource
import com.example.newsapptask.news_feature.data.remote.models.NewsResponse
import com.example.newsapptask.news_feature.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(private val searchNewsUseCase: SearchNewsUseCase) :
    ViewModel() {
    private val _searchNews: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val searchNews: StateFlow<Resource<NewsResponse>> = _searchNews

    private var searchNewsPage = 1
    private var searchNewsResponse: NewsResponse? = null
    private var currentQuery: String = ""

    fun getSearchNews(search: String) = viewModelScope.launch {
        if (search.isNotEmpty() && search != currentQuery) {
            currentQuery = search
            searchNewsPage = 1
            searchNewsResponse = null
        }
        delay(500L)
        val response = searchNewsUseCase(search, searchNewsPage)
        _searchNews.emit(handleSearchNewsResponse(response))
        if (response.isSuccessful) {
            searchNewsPage++
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsResponse = if (searchNewsResponse == null) {
                    resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles ?: mutableListOf()
                    val newArticles = resultResponse.articles
                    oldArticles.addAll(newArticles)
                    NewsResponse(oldArticles, "", 0)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun resetSearch() {
        searchNewsPage = 1
        searchNewsResponse = null
        _searchNews.value = Resource.Loading()
    }
}