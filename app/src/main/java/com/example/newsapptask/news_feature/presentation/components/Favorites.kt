package com.example.newsapptask.news_feature.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.example.newsapptask.news_feature.presentation.viewmodels.FavoritesNewsViewModel
import com.google.gson.Gson

@Composable
fun FavoritesScreen(
    viewModel: FavoritesNewsViewModel= hiltViewModel(),
    navController: NavController
) {
    val articles by viewModel.favoriteArticles.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles) { article ->
            FavoritesItem(
                article = article,
                navController = navController,
                onDelete = { viewModel.deleteFavoriteNews(article) }
            )
        }
    }
}


@Composable
fun FavoritesItem(
    article: Article,
    navController: NavController,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    "article_details?article=${Uri.encode(Gson().toJson(article))}"
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            article.urlToImage?.let { imageUrl ->
                val painter = rememberAsyncImagePainter(model = imageUrl)
                Image(
                    painter = painter,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = article.title ?: "",
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.description ?: "No description available.",
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Author: ${article.author ?: "Unknown"}")
            Text(text = "Published at: ${article.publishedAt ?: "Unknown"}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Article",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
