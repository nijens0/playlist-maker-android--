package com.example.playlistmaker.creator

import com.example.playlistmaker.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.domain.TracksRepository

import com.example.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var historyRepository: SearchHistoryRepository? = null
    private var tracksRepository: TracksRepository? = null

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
}