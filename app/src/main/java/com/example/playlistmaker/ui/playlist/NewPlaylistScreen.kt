package com.example.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.ScreenHeader

@Composable
fun NewPlaylistScreen(
    modifier: Modifier,
    navigateBack: () -> Unit
) {

    Column(
        modifier
    ) {
        ScreenHeader(text = stringResource(R.string.new_playlist)) { }
    }
}