package com.example.playlistmaker.data.database.repositories

import com.example.playlistmaker.data.database.daos.PlaylistsDao
import com.example.playlistmaker.data.database.entities.PlaylistEntity
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val dao: PlaylistsDao
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return dao.getPlaylistWithTracks(playlistId).map { playlistWithTracks ->
            playlistWithTracks?.let { it ->
                Playlist(
                    id = it.playlist.id,
                    name = it.playlist.name,
                    description = it.playlist.description,
                    tracks = it.tracks.map { it.toTrack()}
                )
            }
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylistsWithTracks().map { list ->

            list.map { playlistWithTracks ->
                Playlist(
                    id = playlistWithTracks.playlist.id,
                    name = playlistWithTracks.playlist.name,
                    description = playlistWithTracks.playlist.description,
                    tracks = playlistWithTracks.tracks.map { trackEntity -> trackEntity.toTrack() }
                )
            }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        dao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        dao.deletePlaylistById(id)
    }
}