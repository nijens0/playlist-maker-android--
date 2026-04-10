package com.example.playlistmaker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.network.Track
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.HeaderStyle
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.themes.SecondaryGray


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
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(45.dp),
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "${R.string.track}+${track.trackName}"
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Text(text = track.artistName)
        }
        Column(
            modifier = Modifier.weight(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(track.trackTime)
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