package com.example.playlistmaker.ui.view_model

import com.example.playlistmaker.network.Track

sealed class SearchState {
    data object Initial: SearchState()
    data object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
}