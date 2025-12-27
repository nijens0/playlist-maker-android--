package com.example.playlistmaker.creator

import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.network.RetrofitNetworkClient
import com.example.playlistmaker.network.TracksRepositoryImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}