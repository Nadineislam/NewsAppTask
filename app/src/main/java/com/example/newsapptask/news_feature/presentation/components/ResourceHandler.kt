package com.example.newsapptask.news_feature.presentation.components

import androidx.compose.runtime.Composable
import com.example.newsapptask.core.utils.Resource

@Composable
fun <T> ResourceHandler(
    resource: Resource<T>,
    loadingContent: @Composable () -> Unit,
    successContent: @Composable (T) -> Unit,
    errorContent: @Composable (String?) -> Unit
) {
    when (resource) {
        is Resource.Loading -> loadingContent()
        is Resource.Success -> successContent(resource.data!!)
        is Resource.Error -> errorContent(resource.message)
    }
}
