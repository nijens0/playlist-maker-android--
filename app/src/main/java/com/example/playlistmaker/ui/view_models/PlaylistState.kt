package com.example.playlistmaker.ui.view_models

import androidx.annotation.StringRes
import com.example.playlistmaker.domain.Playlist

sealed class PlaylistState {
    data object Loading: PlaylistState()
    data class Success(val playlist: Playlist): PlaylistState()
    data class Error(@StringRes val resId: Int): PlaylistState()
}