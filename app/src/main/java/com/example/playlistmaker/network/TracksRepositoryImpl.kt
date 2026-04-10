package com.example.playlistmaker.network

import com.example.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl : TracksRepository {

    override suspend fun getALlTracks(): List<Track> {
        delay(1000)
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) ||
                it.artistName.lowercase().contains(expression.lowercase())}
    }
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