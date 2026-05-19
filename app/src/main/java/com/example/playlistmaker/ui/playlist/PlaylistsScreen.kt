package com.example.playlistmaker.ui.playlist

import com.example.playlistmaker.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.network.Playlist
import com.example.playlistmaker.ui.components.PlaylistListItem
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.view_model.PlaylistsViewModel

@Composable
fun PlaylistsScreen(
    modifier: Modifier,
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long?) -> Unit,
    navigateBack: () -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier
        ) {
            ScreenHeader(
                text = stringResource(R.string.playlists),
                onBackClick = navigateBack
            )

            LazyColumn{
                items(playlists.size) { index ->
                    PlaylistListItem(playlist = playlists[index]) {
                        navigateToPlaylist(playlists[index].id)
                    }
                }
            }
        }

        SmallFloatingActionButton (
            modifier = Modifier
                .padding(end = 17.dp, bottom = 32.dp)
                .size(51.dp)
                .align(Alignment.BottomEnd)
                .alpha(0.25f),
            onClick = addNewPlaylist,
            containerColor = Color(0xFF1A1B22),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_playlist)
            )
        }
    }
}