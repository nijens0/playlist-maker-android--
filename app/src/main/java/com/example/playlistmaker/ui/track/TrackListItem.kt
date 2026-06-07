package com.example.playlistmaker.ui.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray

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