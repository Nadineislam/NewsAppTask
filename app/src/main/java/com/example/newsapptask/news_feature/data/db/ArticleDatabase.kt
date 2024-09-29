package com.example.newsapptask.news_feature.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapptask.news_feature.data.remote.models.Article

@Database(entities = [Article::class], version = 4)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}