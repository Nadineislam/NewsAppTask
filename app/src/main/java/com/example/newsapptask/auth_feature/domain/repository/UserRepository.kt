package com.example.newsapptask.auth_feature.domain.repository

import com.example.newsapptask.auth_feature.data.models.User

interface UserRepository {
    suspend fun register(user: User)

    suspend fun login(email: String, password: String): User?
}