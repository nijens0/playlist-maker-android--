package com.example.playlistmaker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Playlist
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.HeaderStyle
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray


@Composable
fun MenuButton(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
        onClick = onClick,
        enabled = true,
        content = content,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun ScreenHeader(text: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 20.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .padding(start = 16.dp, end = 28.dp)
                .size(24.dp),
            onClick = onBackClick,
            enabled = true,
            colors = IconButtonDefaults.iconButtonColors(Color.White)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back)
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = HeaderStyle
        )
    }
}

@Composable
fun TrackListItem(track: Track, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = 13.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .size(45.dp),
            model = track.image,
            contentDescription = "${R.string.track}+${track.trackName}",
            placeholder = painterResource(R.drawable.ic_music),
            error = painterResource(R.drawable.ic_music)
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = track.trackName,
                style = MainTextStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${track.artistName} · ${track.trackTime}",
                style = MainTextStyle.copy(color = PrimaryGray, fontSize = 11.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(
            modifier = Modifier
                .padding(start = 8.dp, end = 12.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}

@Composable
fun PlaylistListItem(playlist: Playlist, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke() }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = playlist.name,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(playlist.name, fontSize = 16.sp)
            val text = "${playlist.tracks.size} ${stringResource(R.string.track)}ов"
            Text(text, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
    ) {
        stickyHeader {
            HorizontalDivider(
                thickness = 1.dp,
                color = PrimaryGray,
                modifier = Modifier
                    .padding(horizontal = 9.5.dp)
            )
        }
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 14.dp, end = 6.dp)
                        .size(16.dp),
                    tint = PrimaryGray
                )
                Text(text = historyList[index], style = CommonTextStyle)
            }
        }
    }
}