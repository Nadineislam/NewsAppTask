package com.example.newsapptask.news_feature.data.db

import androidx.room.TypeConverter
import com.example.newsapptask.news_feature.data.remote.models.Source

class Converters {
    @TypeConverter
    fun toString(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}