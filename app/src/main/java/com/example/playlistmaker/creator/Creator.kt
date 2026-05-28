package com.example.playlistmaker.creator

import com.example.playlistmaker.data.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.domain.NetworkClient
import com.example.playlistmaker.domain.PlaylistsRepository
import com.example.playlistmaker.domain.SearchHistoryRepository
import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.network.ITunesApiService
import com.example.playlistmaker.network.RetrofitNetworkClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object Creator {

    private var tracksRepository: TracksRepository? = null
    private var playlistsRepository: PlaylistsRepository? = null
    private var searchHistoryRepository: SearchHistoryRepository? = null

    private fun getApiService(): ITunesApiService {
        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<ITunesApiService>()
    }

    private fun getRetrofitNetworkClient(): NetworkClient {
        return RetrofitNetworkClient(getApiService())
    }

    fun getTracksRepository(): TracksRepository {
        if (tracksRepository == null) {
            tracksRepository = TracksRepositoryImpl(getRetrofitNetworkClient())
        }
        return tracksRepository!!
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        if (playlistsRepository == null) {
            playlistsRepository = PlaylistsRepositoryImpl()
        }
        return playlistsRepository!!
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        if (searchHistoryRepository == null) {
            searchHistoryRepository = SearchHistoryRepositoryImpl()
        }
        return searchHistoryRepository!!
    }
}