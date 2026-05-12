package com.example.playlistmaker.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.main.MainMenuScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.example.playlistmaker.ui.view_model.SearchViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.playlist.NewPlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistScreen
import com.example.playlistmaker.ui.track.TrackDetails
import com.example.playlistmaker.ui.view_model.PlaylistsViewModel

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.getViewModelFactory()
    )

    val playlistsViewModel: PlaylistsViewModel = viewModel(
        factory = PlaylistsViewModel.getViewModelFactory()
    )

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {

        composable(Routes.MAIN) {
            MainMenuScreen(onClick = { navController.navigate(it) } )
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                searchViewModel = searchViewModel,
                onClick = { track ->
                    searchViewModel.selectedTrack = track
                    navController.navigate(Routes.TRACK_DETAILS)
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.PLAYLISTS) {
            PlaylistScreen(
                modifier,
                playlistsViewModel,
                addNewPlaylist = { navController.navigate(Routes.NEW_PLAYLIST) },
                navigateToPlaylist = {},
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.NEW_PLAYLIST) {
            NewPlaylistScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TRACK_DETAILS) {
            val selectedTrack = searchViewModel.selectedTrack
            if (selectedTrack != null) {
                TrackDetails(
                    modifier = modifier,
                    track = selectedTrack,
                    playlistsViewModel = playlistsViewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(Routes.FAVORITES) {
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