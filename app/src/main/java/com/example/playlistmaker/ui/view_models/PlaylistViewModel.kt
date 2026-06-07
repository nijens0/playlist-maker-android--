package com.example.playlistmaker.ui.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.PlaylistsRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import com.example.playlistmaker.R
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long
) : ViewModel() {

    val playlistScreenState: StateFlow<PlaylistState> = playlistsRepository.getPlaylist(playlistId)
        .map{ playlist ->
            if (playlist != null) {
                PlaylistState.Success(playlist)
            } else {
                PlaylistState.Error(R.string.playlist_is_not_found)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlaylistState.Loading
        )


    companion object {
        fun getViewModelFactory(playlistId: Long, context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val tracksRepository = Creator.getPlaylistsRepository(context)

                    return PlaylistViewModel(tracksRepository, playlistId) as T
                }
            }
    }
}