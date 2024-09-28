package com.example.newsapptask.news_feature.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.newsapptask.news_feature.presentation.viewmodels.SearchNewsViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchNewsViewModel = hiltViewModel(), navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val searchMealState = viewModel.searchNews.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { query ->
                searchQuery.value = query
                viewModel.resetSearch()
                viewModel.getSearchNews(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = "Search for news...") }
        )

        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = lazyListState.layoutInfo.totalItemsCount
                    if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItems - 5) {
                        viewModel.getSearchNews(searchQuery.value)
                    }
                }
        }

        ResourceHandler(
            resource = searchMealState.value,
            loadingContent = {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            },
            successContent = { resource ->
                val articleList = resource.articles
                if (articleList.isNotEmpty()) {
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(articleList) { article ->
                            NewsItem(article = article, onClick = {
                                navController.navigate(
                                    "article_details?article=${Uri.encode(Gson().toJson(article))}"
                                )
                            })
                        }
                    }
                } else {
                    Text(
                        text = "No articles found",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            },
            errorContent = { errorMessage ->
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        )
    }
}
