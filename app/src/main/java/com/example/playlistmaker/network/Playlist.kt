package com.example.playlistmaker.network

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tracks: List<Track>
)