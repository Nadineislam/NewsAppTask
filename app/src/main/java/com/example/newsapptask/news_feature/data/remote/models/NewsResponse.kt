package com.example.newsapptask.news_feature.data.remote.models

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
