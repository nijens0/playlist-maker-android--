package com.example.playlistmaker.creator

import com.example.playlistmaker.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.data.TracksRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var historyRepository: SearchHistoryRepositoryImpl? = null

    fun getSearchHistoryRepository(): SearchHistoryRepositoryImpl {
        if (historyRepository == null) {
            historyRepository = SearchHistoryRepositoryImpl(scope = appScope)
        }
        return historyRepository!!
    }

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl()
    }
}