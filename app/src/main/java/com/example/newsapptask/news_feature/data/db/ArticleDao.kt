package com.example.newsapptask.news_feature.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsapptask.news_feature.data.remote.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNews(article: List<Article>)

    @Query("SELECT * FROM articles ")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE favorite = 1")
    fun getFavoriteArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteNews(article: Article)

    @Query("SELECT * FROM articles WHERE category = :category")
    fun getArticlesByCategory(category: String): Flow<List<Article>>

    @Transaction
    suspend fun upsertFavoriteNews(article: Article) {
        upsertNews(listOf(article.copy(favorite = true)))

    }

    @Transaction
    suspend fun deleteFavoriteNews(article: Article) {
        deleteNews(article.copy(favorite = false))
    }
}