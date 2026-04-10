package com.example.playlistmaker.domain

import com.example.playlistmaker.network.Track

interface TracksRepository {
    suspend fun getALlTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}