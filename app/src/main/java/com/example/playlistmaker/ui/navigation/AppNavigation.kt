package com.example.playlistmaker.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.main.MainMenuScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.example.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainMenuScreen(navController)
        }
        composable(Routes.SEARCH) {
            val viewModel: SearchViewModel = viewModel(
                factory = SearchViewModel.getViewModelFactory()
            )
            SearchScreen(navController, viewModel)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }
        composable(Routes.PLAYLISTS) {
            PlaceholderScreen("Playlists", navController) { navController.popBackStack() }
        }
        composable(Routes.FAVORITES) {
            PlaceholderScreen("Favorites", navController) { navController.popBackStack() }
        }
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    navController: androidx.navigation.NavController,
    onBack: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        ScreenHeader(title, onBack)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Screen: $title")
        }
    }
}