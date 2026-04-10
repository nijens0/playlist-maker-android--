package com.example.playlistmaker.network

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favourite: Boolean,
    var playlistId: Long
)