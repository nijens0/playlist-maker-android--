package com.example.playlistmaker.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.themes.HeaderStyle

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