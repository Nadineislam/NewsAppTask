package com.example.newsapptask.news_feature.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapptask.news_feature.data.remote.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNews(article: Article)

    @Query("SELECT * FROM articles ")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteNews(article: Article)
}