package com.example.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.network.TracksRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {
    private val tracksRepository = TracksRepositoryImpl()
    private val searchHistoryRepository = SearchHistoryRepositoryImpl(scope = viewModelScope)

    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(word = request)

                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    fun getHistoryList(): Flow<List<String>> = searchHistoryRepository.getHistoryRequests()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel() as T
                }
            }
    }
}