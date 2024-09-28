package com.example.newsapptask.news_feature.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter

@Composable
fun LazyListState.onBottomReached(
    buffer: Int = 0,
    loadMore: () -> Unit
) {
    val shouldLoadMoreBuffer = remember(layoutInfo.totalItemsCount) {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
            val bufferIndex = layoutInfo.totalItemsCount - 1 - buffer
            lastVisibleItem.index >= bufferIndex
        }
    }

    LaunchedEffect(shouldLoadMoreBuffer) {
        snapshotFlow { shouldLoadMoreBuffer.value }
            .filter { it }
            .collect { loadMore() }
    }
}