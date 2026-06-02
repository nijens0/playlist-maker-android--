package com.example.playlistmaker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.playlistmaker.data.database.daos.PlaylistsDao
import com.example.playlistmaker.data.database.daos.TracksDao
import com.example.playlistmaker.data.database.entities.Converters
import com.example.playlistmaker.data.database.entities.PlaylistEntity
import com.example.playlistmaker.data.database.entities.PlaylistTrackCrossRef
import com.example.playlistmaker.data.database.entities.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun TracksDao(): TracksDao

    abstract fun PlaylistsDao(): PlaylistsDao
}