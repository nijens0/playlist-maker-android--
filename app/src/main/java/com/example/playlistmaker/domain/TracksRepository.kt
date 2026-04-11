package com.example.playlistmaker.domain

import com.example.playlistmaker.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun getALlTracks(): List<Track>

    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavouriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlayList(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)

    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    suspend fun updateTrackFavouriteStatus(track: Track, isFavourite: Boolean)
}