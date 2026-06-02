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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.main.MainMenuScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.example.playlistmaker.ui.view_model.SearchViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.playlist.NewPlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistsScreen
import com.example.playlistmaker.ui.track.TrackDetails
import com.example.playlistmaker.ui.view_model.PlaylistViewModel
import com.example.playlistmaker.ui.view_model.PlaylistsViewModel

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.getViewModelFactory(LocalContext.current.applicationContext)
    )

    val playlistsViewModel: PlaylistsViewModel = viewModel(
        factory = PlaylistsViewModel.getViewModelFactory(LocalContext.current.applicationContext)
    )

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN.route
    ) {

        composable(Routes.MAIN.route) {
            MainMenuScreen(onClick = { navController.navigate(it) })
        }

        composable(Routes.SEARCH.route) {
            SearchScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                searchViewModel = searchViewModel,
                navigateToTrackDetails = { track ->
                    searchViewModel.selectedTrack = track
                    navController.navigate(Routes.TRACK_DETAILS.route)
                }
            )
        }

        composable(Routes.SETTINGS.route) {
            SettingsScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PLAYLISTS.route) {
            PlaylistsScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navController.navigate(Routes.NEW_PLAYLIST.route) },
                navigateToPlaylist = { id ->
                    navController.navigate(Routes.PLAYLIST.withArgs(id))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Routes.PLAYLIST.route}/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L

            val context = LocalContext.current.applicationContext

            val playlistViewModel: PlaylistViewModel = viewModel(
                factory = PlaylistViewModel.getViewModelFactory(
                    playlistId = playlistId,
                    context = context
                )
            )

            PlaylistScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                playlistViewModel = playlistViewModel,
                navigateToTrackDetails = { track ->
                    searchViewModel.selectedTrack = track
                    navController.navigate(Routes.TRACK_DETAILS.route)
                }
            )
        }

        composable(Routes.NEW_PLAYLIST.route) {
            NewPlaylistScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TRACK_DETAILS.route) {
            val track = searchViewModel.selectedTrack

            if (track != null) {
                TrackDetails(
                    modifier = modifier,
                    track = track,
                    playlistsViewModel = playlistsViewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(Routes.FAVORITES.route) {
            val title = stringResource(id = R.string.favourite)
            PlaceholderScreen(title) { navController.popBackStack() }
        }
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ScreenHeader(title, onBack)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Screen: $title")
        }
    }
}