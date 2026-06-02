package com.example.playlistmaker.ui.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.domain.Playlist
import com.example.playlistmaker.domain.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()
    val favouriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun createNewPlaylist(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch {
            tracksRepository.insertTrackToPlayList(track, playlistId)
        }
    }

    suspend fun toggleFavourite(track: Track, isFavourite: Boolean) {
        tracksRepository.updateTrackFavouriteStatus(track, isFavourite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track, playlistId: Long) {
        tracksRepository.deleteTrackFromPlaylist(playlistId,track.id)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track).firstOrNull()
    }

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val tracksRepository = Creator.getTracksRepository(context)
                    val playlistsRepository = Creator.getPlaylistsRepository(context)
                    return PlaylistsViewModel(
                        tracksRepository = tracksRepository,
                        playlistsRepository = playlistsRepository
                    ) as T
                }
            }
    }
}