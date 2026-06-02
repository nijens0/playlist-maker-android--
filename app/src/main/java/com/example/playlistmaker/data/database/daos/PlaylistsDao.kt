package com.example.playlistmaker.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.playlistmaker.data.database.entities.PlaylistEntity
import com.example.playlistmaker.data.database.entities.PlaylistTrackCrossRef
import com.example.playlistmaker.data.database.entities.PlaylistWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToPlaylistCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId")
    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :id")
    fun getPlaylistWithTracks(id: Long): Flow<PlaylistWithTracks?>

    @Query("SELECT * FROM playlists WHERE id = :id")
    fun getPlaylist(id: Long): Flow<PlaylistEntity?>

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistsWithTracks(): Flow<List<PlaylistWithTracks>>

    @Query("DELETE FROM playlists WHERE id = :id")
    fun deletePlaylistById(id: Long)
}