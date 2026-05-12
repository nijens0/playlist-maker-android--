package com.example.playlistmaker.ui.track

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.playlistmaker.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.network.Track
import com.example.playlistmaker.ui.components.PlaylistListItem
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.view_model.PlaylistsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetails(
    modifier: Modifier,
    track: Track,
    playlistsViewModel: PlaylistsViewModel,
    navigateBack: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(track.favourite) }
    var isShowPanel by remember { mutableStateOf(false) }
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isFavorite) Color.Red else PrimaryGray,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val sheetState = rememberModalBottomSheetState()
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            text = "",
            onBackClick = navigateBack
        )
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(312.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp),
                imageVector = Icons.Default.MusicVideo,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = track.trackName,
                style = MainTextStyle.copy(fontSize = 22.sp)
            )
            Text(
                modifier = Modifier.padding(bottom = 54.dp),
                text = track.artistName,
                style = MainTextStyle.copy(fontSize = 14.sp)
            )
            Row(
                modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .background(PrimaryGray, shape = CircleShape),
                    onClick = { isShowPanel = !isShowPanel }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddToPhotos,
                        contentDescription = stringResource(R.string.add_to_playlist),
                        tint = Color.White
                    )
                }
                IconButton(
                    modifier = Modifier
                        .background(animatedBackgroundColor,
                        shape = CircleShape),
                    onClick = {
                        isFavorite = !isFavorite
                        track.favourite = isFavorite
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.add_to_favourite),
                        tint = Color.White
                    )
                }
            }
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.duration),
                    style = MainTextStyle.copy(fontSize = 13.sp, color = PrimaryGray)
                )
                Text(
                    text = track.trackTime,
                    style = MainTextStyle.copy(fontSize = 13.sp)
                )
            }
        }
    }

    if (isShowPanel) {
        ModalBottomSheet(
            onDismissRequest = { isShowPanel = false },
            sheetState = sheetState
        ) {
            LazyColumn {
                items(playlists.size) { index ->
                    PlaylistListItem(playlist = playlists[index]) {
                        track.playlistId = playlists[index].id
                    }
                }
            }
        }
    }
}