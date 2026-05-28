package com.example.playlistmaker.data

import com.example.playlistmaker.domain.NetworkClient
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.network.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    private val trackTimeFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    override suspend fun getALlTracks(): List<Track> = emptyList()

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(dto = TracksSearchRequest(expression))

        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { trackDto ->
                Track(
                    id = trackDto.id,
                    trackName = trackDto.trackName,
                    artistName = trackDto.artistName,
                    trackTime = trackTimeFormatter.format(trackDto.trackTimeMillis),
                    image = trackDto.image?.replace("100x100bb.jpg", "512x512bb.jpg") ?: "",
                    favourite = false,
                    playlistId = 0,
                )
            }
        } else emptyList()
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return flow { emit(null) }
    }

    override suspend fun insertTrackToPlayList(track: Track, playlistId: Long) {
        // Для Room в будущем
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        // Для Room в будущем
    }

    override suspend fun updateTrackFavouriteStatus(track: Track, isFavourite: Boolean) {
        // Для Room в будущем
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        // Для Room в будущем
    }

    override fun getFavouriteTracks(): Flow<List<Track>> {

        return flow{ emit(emptyList()) }
    }

}