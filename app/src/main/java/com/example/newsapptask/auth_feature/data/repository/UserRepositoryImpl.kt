package com.example.newsapptask.auth_feature.data.repository

import com.example.newsapptask.auth_feature.data.db.UserDao
import com.example.newsapptask.auth_feature.data.models.User
import com.example.newsapptask.auth_feature.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun register(user: User) {
        userDao.insert(user)
    }

    override suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}
