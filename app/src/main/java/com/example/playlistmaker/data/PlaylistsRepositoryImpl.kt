package com.example.playlistmaker.data

import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.network.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsRepositoryImpl(

): PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return flow { emit(null) }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {

    }

    override suspend fun deletePlaylistById(id: Long) {

    }

}