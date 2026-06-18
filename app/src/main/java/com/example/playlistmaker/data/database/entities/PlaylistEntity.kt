package com.example.playlistmaker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.playlistmaker.domain.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String,
    val tracks: List<Track> = emptyList()
)

class Converters {
    @TypeConverter
    fun fromTrackList(value: List<Track>): String = Gson().toJson(value)

    @TypeConverter
    fun toTrackList(value: String): List<Track> =
        Gson().fromJson(value, object : TypeToken<List<Track>>() {}.type)
}