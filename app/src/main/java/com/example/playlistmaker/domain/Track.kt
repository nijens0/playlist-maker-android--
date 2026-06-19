package com.example.playlistmaker.domain

import com.example.playlistmaker.data.database.entities.TrackEntity

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favourite: Boolean
) {
    fun toEntity(): TrackEntity {
        return TrackEntity(
            id = this.id,
            trackName = this.trackName,
            artistName = this.artistName,
            trackTime = this.trackTime,
            image = this.image,
            favorite = this.favourite
        )
    }
}