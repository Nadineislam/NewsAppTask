package com.example.newsapptask.auth_feature.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapptask.auth_feature.data.models.User

@Database(entities = [User::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

