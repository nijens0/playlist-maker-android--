package com.example.playlistmaker.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.ui.navigation.ScreenHeader
import com.example.playlistmaker.ui.view_models.PlaylistsViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.track.TrackListItem

@Composable
fun FavoriteScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    playlistsViewModel: PlaylistsViewModel,
    navigateToTrackDetails: (Track?) -> Unit
) {
    val favoriteList by playlistsViewModel.favouriteList.collectAsState(emptyList())

    Column(modifier) {
        ScreenHeader(
            text = stringResource(R.string.favourite),
            onBackClick = navigateBack
        )
        if (!favoriteList.isEmpty()) {
            LazyColumn {
                items(favoriteList.size) { index ->
                    val track = favoriteList[index]
                    TrackListItem(
                        track = track,
                        onClick = { navigateToTrackDetails(track) },
                        onLongClick = { }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 154.dp, bottom = 16.dp)
                        .width(120.dp)
                        .height(120.dp),
                    painter = painterResource(R.drawable.track_is_not_found),
                    contentDescription = stringResource(R.string.library_is_empty)
                )
                Text(
                    text = stringResource(R.string.library_is_empty),
                    style = MainTextStyle
                )
            }
        }
    }
}