package com.example.newsapptask.auth_feature.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapptask.auth_feature.data.models.User

@Dao
interface UserDao {
    @Upsert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?
}