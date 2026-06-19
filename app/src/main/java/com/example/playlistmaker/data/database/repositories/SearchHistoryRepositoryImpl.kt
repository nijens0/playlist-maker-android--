package com.example.playlistmaker.data.database.repositories

import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
import com.example.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences
): SearchHistoryRepository {

    override fun addToHistory(word: String) {
        searchHistoryPreferences.addEntry(word)
    }

    override fun getHistoryRequests(): Flow<List<String>> {
        return searchHistoryPreferences.getEntries()
    }
}