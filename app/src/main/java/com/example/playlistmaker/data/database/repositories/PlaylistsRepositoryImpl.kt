package com.example.playlistmaker.data.database.repositories

import android.content.Context
import com.example.playlistmaker.data.database.daos.PlaylistsDao
import com.example.playlistmaker.data.database.entities.PlaylistEntity
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class PlaylistsRepositoryImpl(
    private val context: Context,
    private val dao: PlaylistsDao
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return dao.getPlaylistWithTracks(playlistId).map { playlistWithTracks ->
            playlistWithTracks?.let { it ->
                Playlist(
                    id = it.playlist.id,
                    name = it.playlist.name,
                    description = it.playlist.description,
                    tracks = it.tracks.map { it.toTrack()},
                    coverImageUri = playlistWithTracks.playlist.coverImageUri
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
                    tracks = playlistWithTracks.tracks.map { trackEntity -> trackEntity.toTrack() },
                    coverImageUri = playlistWithTracks.playlist.coverImageUri
                )
            }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        val localStoragePath = saveImageToPrivateStorage(coverImageUri)

        dao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = localStoragePath
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        dao.deletePlaylistById(id)
    }

    private fun saveImageToPrivateStorage(uriString: String?): String {
        if (uriString.isNullOrBlank()) return ""

        return try {
            val uri = uriString.toUri()

            val imageDir = context.getDir("playlist_covers", Context.MODE_PRIVATE)
            if (!imageDir.exists()) {
                imageDir.mkdirs()
            }

            val file = File(imageDir, "cover_${System.currentTimeMillis()}.jpg")

            context.contentResolver.openInputStream(uri).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}