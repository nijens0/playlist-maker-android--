package com.example.playlistmaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlistmaker.ui.main.MainMenuScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.example.playlistmaker.ui.view_models.SearchViewModel
import com.example.playlistmaker.ui.favorite.FavoriteScreen
import com.example.playlistmaker.ui.playlist.NewPlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistsScreen
import com.example.playlistmaker.ui.track.TrackDetails
import com.example.playlistmaker.ui.view_models.NewPlaylistViewModel
import com.example.playlistmaker.ui.view_models.PlaylistViewModel
import com.example.playlistmaker.ui.view_models.PlaylistsViewModel

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.getViewModelFactory(context)
    )

    val playlistsViewModel: PlaylistsViewModel = viewModel(
        factory = PlaylistsViewModel.getViewModelFactory(context)
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

            val newPlaylistViewModel: NewPlaylistViewModel = viewModel(
                factory = NewPlaylistViewModel.getViewModelFactory(
                    context = context
                )
            )
            NewPlaylistScreen(
                modifier = modifier,
                newPlaylistViewModel = newPlaylistViewModel,
                navigateBack = { navController.popBackStack() },
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
            FavoriteScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                playlistsViewModel = playlistsViewModel,
                navigateToTrackDetails = { track ->
                    searchViewModel.selectedTrack = track
                    navController.navigate(Routes.TRACK_DETAILS.route)
                }
            )
        }
    }
}