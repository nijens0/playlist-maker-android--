package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {

    suspend fun getAllTracks(): List<Track>

    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlayList(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)

    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    suspend fun updateTrackFavouriteStatus(track: Track, isFavourite: Boolean)
}