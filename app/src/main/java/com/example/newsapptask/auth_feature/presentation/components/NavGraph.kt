package com.example.newsapptask.auth_feature.presentation.components

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapptask.auth_feature.data.datastore.LoginDataStore
import com.example.newsapptask.news_feature.NewsActivity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph( loginDataStore: LoginDataStore) {
    val navController = rememberNavController()
    val isLoggedIn by loginDataStore.loginState.collectAsState(initial = false)
    val context = LocalContext.current

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            context.startActivity(Intent(context, NewsActivity::class.java))

            (context as? Activity)?.finish()
        }
    }

    NavHost(navController = navController, startDestination =  "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
    }
}