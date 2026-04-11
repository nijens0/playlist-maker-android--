package com.example.playlistmaker.data

import DatabaseMock
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.network.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val scope: CoroutineScope
): PlaylistsRepository {
    private val database = DatabaseMock(scope = scope)


    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(
            name = name,
            description = description
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }


}