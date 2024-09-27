package com.example.newsapptask.auth_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.auth_feature.data.models.User
import com.example.newsapptask.auth_feature.domain.usecase.RegisterUseCase
import com.example.newsapptask.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    val username = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _registrationState = MutableSharedFlow<Resource<Boolean>>()
    val registrationState get() = _registrationState

    fun register() {
        viewModelScope.launch {
            _registrationState.emit(Resource.Loading())
            try {
                val user =
                    User(username = username.value, email = email.value, password = password.value)
                registerUseCase(user)
                _registrationState.emit(Resource.Success(true))
            } catch (e: Exception) {
                _registrationState.emit(Resource.Error(e.message ?: "Registration failed"))
            }
        }
    }
}