package com.example.newsapptask.news_feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapptask.news_feature.data.remote.models.Article

@Composable
fun NewsList(newsArticles: List<Article>, onArticleClick: (Article) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(newsArticles) { article ->
            NewsItem(article = article, onClick = {
                onArticleClick(article)
            })
        }
    }
}

@Composable
fun NewsItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            article.urlToImage?.let { imageUrl ->
                val painter = rememberAsyncImagePainter(
                    model = imageUrl
                )
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

            Text(text = article.title ?: "", fontWeight = FontWeight.Bold, maxLines = 2)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = article.description ?: "No description available.", maxLines = 1)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Author: ${article.author ?: "Unknown"}")

            Text(text = "Published at: ${article.publishedAt ?: "Unknown"}")
        }
    }
}
@Composable
fun BreakingNewsItem(article: Article,onClick: () -> Unit) {
    Box {
        Card(
            modifier = Modifier
                .background(Color.White)
                .padding(6.dp)
                .height(170.dp)
                .width(270.dp)
                .clickable (onClick = onClick),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(modifier = Modifier.background(Color.White)) {
                val painter = rememberAsyncImagePainter(
                    model = article.urlToImage
                )
                Image(
                    painter = painter,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                )
                Text(
                    text = article.title ?: "No Title",
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
