package com.example.playlistmaker.data.database.repositories

import com.example.playlistmaker.data.TracksSearchRequest
import com.example.playlistmaker.data.TracksSearchResponse
import com.example.playlistmaker.data.database.daos.PlaylistsDao
import com.example.playlistmaker.data.database.daos.TracksDao
import com.example.playlistmaker.data.database.entities.PlaylistTrackCrossRef
import com.example.playlistmaker.domain.NetworkClient
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(
    private val tracksDao: TracksDao,
    private val playlistsDao: PlaylistsDao,
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun getAllTracks(): List<Track> =
        tracksDao.getAllTracks().map { it.toTrack() }

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(dto = TracksSearchRequest(expression))
        val trackTimeFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())

        return when (response.resultCode) {
            200 -> {
                (response as TracksSearchResponse).results.map { trackDto ->
                    Track(
                        id = trackDto.id,
                        trackName = trackDto.trackName,
                        artistName = trackDto.artistName,
                        trackTime = trackTimeFormatter.format(trackDto.trackTimeMillis),
                        image = trackDto.image?.replace("100x100bb.jpg", "512x512bb.jpg") ?: "",
                        favourite = false
                    )
                }
            }
            -1 -> throw IOException("No internet connection")
            else -> throw Exception("Internal server error with code: ${response.resultCode}")
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return tracksDao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override suspend fun insertTrackToPlayList(track: Track, playlistId: Long) {
        val trackEntity = track.toEntity()
        tracksDao.insertTrack(trackEntity)

        val crossRef = PlaylistTrackCrossRef(playlistId = playlistId, trackId = track.id)
        playlistsDao.insertTrackToPlaylistCrossRef(crossRef)
    }

    override suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long) {
        playlistsDao.deleteTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun updateTrackFavouriteStatus(track: Track, isFavorite: Boolean) {
        tracksDao.insertTrack(track.copy(favourite = isFavorite).toEntity())
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        playlistsDao.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksDao.getFavoriteTracks().map { list ->
            list.filterNotNull().map { it.toTrack() }
        }
    }

}