package com.example.playlistmaker.data

import com.example.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchHistoryRepositoryImpl(

): SearchHistoryRepository {

    override fun getHistoryRequests(): Flow<List<String>> {
        return flow { emit(emptyList()) }
    }

    override fun addToHistory(word: String) {

    }
}