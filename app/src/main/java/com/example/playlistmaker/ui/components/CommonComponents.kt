package com.example.playlistmaker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.network.Track
import com.example.playlistmaker.ui.themes.HeaderStyle


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
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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