package com.example.playlistmaker.ui.view_model

import androidx.annotation.StringRes
import com.example.playlistmaker.network.Playlist

sealed class PlaylistState {
    data object Loading: PlaylistState()
    data class Success(val playlist: Playlist): PlaylistState()
    data class Error(@StringRes val resId: Int): PlaylistState()
}