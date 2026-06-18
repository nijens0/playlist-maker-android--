package com.example.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.ui.navigation.ScreenHeader
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.track.TrackListItem
import com.example.playlistmaker.ui.view_models.PlaylistState
import com.example.playlistmaker.ui.view_models.PlaylistViewModel

@Composable
fun PlaylistScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    playlistViewModel: PlaylistViewModel,
    navigateToTrackDetails: (Track?) -> Unit
) {
    val playlistState by playlistViewModel.playlistScreenState.collectAsState()

    when (playlistState) {
        is PlaylistState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PlaylistState.Success -> {
            val playlist = (playlistState as PlaylistState.Success).playlist
            Column(modifier) {
                ScreenHeader(
                    text = "",
                    onBackClick = navigateBack
                )
                AsyncImage(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .size(312.dp)
                        .align(Alignment.CenterHorizontally),
                    model = playlist.coverImageUri,
                    contentDescription = stringResource(R.string.playlist_image),
                    error = painterResource(R.drawable.add_photo),
                    placeholder = painterResource(R.drawable.add_photo)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = playlist.name,
                        style = MainTextStyle.copy(fontSize = 24.sp)
                    )
                    val totalMinutes = playlist.tracks.sumOf { track ->
                        val parts = track.trackTime.split(":")
                        val minutes = parts.getOrNull(0)?.toLongOrNull() ?: 0L
                        val seconds = parts.getOrNull(1)?.toLongOrNull() ?: 0L
                        (minutes * 60) + seconds
                    } / 60
                    val tracks = playlist.tracks
                    Text(
                        text = "$totalMinutes минут • ${tracks.size} треков"
                    )
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more)
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(playlist.tracks.size) { index ->
                            TrackListItem(
                                track = tracks[index],
                                onClick = { navigateToTrackDetails(tracks[index])},
                                onLongClick = { playlistViewModel
                                    .deleteTrackFromPlaylist(playlist.id, tracks[index])
                                }
                            )
                        }
                    }
                }
            }
        }

        is PlaylistState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }
}