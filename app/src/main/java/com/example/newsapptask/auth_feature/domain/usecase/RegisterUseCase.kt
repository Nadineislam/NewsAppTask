package com.example.newsapptask.auth_feature.domain.usecase

import com.example.newsapptask.auth_feature.data.models.User
import com.example.newsapptask.auth_feature.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) = userRepository.register(user)

}