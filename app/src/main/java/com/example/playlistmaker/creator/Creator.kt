package com.example.playlistmaker.creator

import DatabaseMock
import com.example.playlistmaker.data.PlaylistRepositoryImpl
import com.example.playlistmaker.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var historyRepository: SearchHistoryRepository? = null
    private var tracksRepository: TracksRepository? = null
    private var playlistsRepository: PlaylistsRepository? = null
    private var databaseRepository: DatabaseMock? = null

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        if (historyRepository == null) {
            historyRepository = SearchHistoryRepositoryImpl(scope = appScope)
        }
        return historyRepository!!
    }

    fun getTracksRepository(): TracksRepository {
        if (tracksRepository == null){
            tracksRepository = TracksRepositoryImpl(scope = appScope)
        }
        return tracksRepository!!
    }

    fun getPlaylistRepository(): PlaylistsRepository {
        if (playlistsRepository == null) {
            playlistsRepository = PlaylistRepositoryImpl(scope = appScope)
        }
        return playlistsRepository!!
    }

    fun getDataBaseRepository(): DatabaseMock {
        if (databaseRepository == null) {
            databaseRepository = DatabaseMock(scope = appScope)
        }
        return databaseRepository!!
    }
}