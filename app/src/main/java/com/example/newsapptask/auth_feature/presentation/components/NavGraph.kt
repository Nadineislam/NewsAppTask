package com.example.newsapptask.auth_feature.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapptask.HomeScreen
import com.example.newsapptask.auth_feature.data.datastore.LoginDataStore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph( loginDataStore: LoginDataStore) {
    val navController = rememberNavController()
    val isLoggedIn by loginDataStore.loginState.collectAsState(initial = false)

    NavHost(navController = navController, startDestination = if (isLoggedIn) "home" else "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen() }
    }
}