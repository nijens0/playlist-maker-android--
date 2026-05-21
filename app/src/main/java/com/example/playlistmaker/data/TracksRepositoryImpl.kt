package com.example.playlistmaker.data

import DatabaseMock
import androidx.room.Database
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    scope: CoroutineScope
) : TracksRepository {
    private val database = DatabaseMock(scope = scope)

    override suspend fun getALlTracks(): List<Track> {
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        return listTracks.filter {
            it.trackName.lowercase().contains(expression.lowercase()) ||
                    it.artistName.lowercase().contains(expression.lowercase())
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlayList(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavouriteStatus(track: Track, isFavourite: Boolean) {
        database.insertTrack(track.copy(favourite = isFavourite))
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTrackByPlaylistId(playlistId)
    }

    override fun getFavouriteTracks(): Flow<List<Track>> {
        return database.getFavouriteTracks()
    }

    val listTracks = listOf(
        Track(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "02:38",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTime = "04:43",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 3,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTime = "05:12",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 4,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTime = "03:45",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 5,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTime = "04:32",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 6,
            trackName = "На заре",
            artistName = "Альянс",
            trackTime = "03:50",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 7,
            trackName = "Перемен",
            artistName = "Кино",
            trackTime = "04:56",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 8,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTime = "03:15",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 9,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTime = "03:42",
            image = "",
            favourite = false,
            playlistId = 0
        ),
        Track(
            id = 10,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTime = "04:01",
            image = "",
            favourite = false,
            playlistId = 0
        )
    )
}