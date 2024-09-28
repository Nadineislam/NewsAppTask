package com.example.newsapptask.news_feature.presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapptask.news_feature.presentation.viewmodels.HomeNewsViewModel
import com.google.gson.Gson

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeNewsViewModel = hiltViewModel()) {
    val breakingNews by viewModel.breakingNews.collectAsState()
    val newsByCategory by viewModel.newsByCategory.collectAsState()
    val lazyListState = rememberLazyListState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ResourceHandler(
            resource = breakingNews,
            loadingContent = {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            },
            successContent = { newsResponse ->
                LazyRow(
                    state = lazyListState,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    val articles = newsResponse.articles
                    items(articles, key = { article ->
                        "${article.title}-${article.publishedAt}-${article.url?.hashCode()}"
                    }) { article ->
                        BreakingNewsItem(article, onClick = {
                            navController.navigate(
                                "article_details?article=${Uri.encode(Gson().toJson(article))}"
                            )
                        })

                        lazyListState.onBottomReached(buffer = 5) {
                            viewModel.getBreakingNews("us")
                        }
                    }
                }
            },
            errorContent = { message ->
                Toast.makeText(LocalContext.current, message ?: "Error loading breaking news", Toast.LENGTH_LONG).show()
            }
        )

        NewsCategoryChips(
            categories = viewModel.categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                viewModel.getNewsByCategory(category)
            }
        )

        ResourceHandler(
            resource = newsByCategory,
            loadingContent = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            },
            successContent = { newsResponse ->
                val articles = newsResponse.articles
                NewsList(
                    newsArticles = articles,
                    onArticleClick = { article ->
                        navController.navigate(
                            "article_details?article=${Uri.encode(Gson().toJson(article))}"
                        )
                    }
                )
            },
            errorContent = { message ->
                Toast.makeText(LocalContext.current, message ?: "An error occurred", Toast.LENGTH_LONG).show()
            }
        )
    }
}