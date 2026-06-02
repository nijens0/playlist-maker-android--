package com.example.playlistmaker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmaker.domain.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val favorite: Boolean = false
) {
    fun toTrack(): Track {
        return Track(
            id = this.id,
            trackName = this.trackName,
            artistName = this.artistName,
            trackTime = this.trackTime,
            image = this.image,
            favourite = this.favorite
        )
    }
}