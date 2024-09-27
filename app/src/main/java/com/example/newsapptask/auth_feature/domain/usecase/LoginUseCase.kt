package com.example.newsapptask.auth_feature.domain.usecase

import com.example.newsapptask.auth_feature.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        userRepository.login(email, password)
}