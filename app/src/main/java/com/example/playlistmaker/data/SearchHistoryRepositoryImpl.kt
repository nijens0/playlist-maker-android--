package com.example.playlistmaker.data

import DatabaseMock
import com.example.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class SearchHistoryRepositoryImpl(scope: CoroutineScope): SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override fun getHistoryRequests(): Flow<List<String>> {
        return database.historyUpdates
            .onStart { emit(Unit) }
            .map {
                database.getHistoryRequests().map { it.word }
            }
    }

    override fun addToHistory(word: String) {
        database.addToHistory(text = word)
    }
}