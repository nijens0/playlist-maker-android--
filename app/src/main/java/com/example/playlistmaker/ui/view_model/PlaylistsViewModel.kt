package com.example.playlistmaker.ui.view_model

import DatabaseMock
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.TracksRepository

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
    private val databaseRepository: DatabaseMock
): ViewModel() {

}