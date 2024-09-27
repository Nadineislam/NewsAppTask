package com.example.newsapptask.auth_feature.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.auth_feature.data.datastore.LoginDataStore
import com.example.newsapptask.auth_feature.data.models.User
import com.example.newsapptask.auth_feature.domain.usecase.LoginUseCase
import com.example.newsapptask.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginDataStore: LoginDataStore // Inject the LoginDataStore
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _loginState = MutableSharedFlow<Resource<User?>>()
    val loginState: SharedFlow<Resource<User?>> get() = _loginState

    fun login() {
        viewModelScope.launch {
            _loginState.emit(Resource.Loading())
            try {
                val user = loginUseCase(email.value, password.value)
                if (user != null) {
                    // Save login state after successful login
                    loginDataStore.saveLoginState(true)
                    _loginState.emit(Resource.Success(user))
                } else {
                    _loginState.emit(Resource.Error("Incorrect email or password"))
                }
            } catch (e: Exception) {
                _loginState.emit(Resource.Error(e.message ?: "Login failed"))
            }
        }
    }
}
