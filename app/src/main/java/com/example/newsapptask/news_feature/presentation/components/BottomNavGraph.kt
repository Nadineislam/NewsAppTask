package com.example.newsapptask.news_feature.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapptask.news_feature.data.remote.models.Article
import com.google.gson.Gson

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(BottomBarScreen.Favorites.route) {
            FavoritesScreen( navController = navController)
        }
        composable(BottomBarScreen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(
            "article_details?article={article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            val article = articleJson?.let { Gson().fromJson(it, Article::class.java) }

            article?.let {
                ArticleDetailsScreen(article = it)
            }
        }
    }
}