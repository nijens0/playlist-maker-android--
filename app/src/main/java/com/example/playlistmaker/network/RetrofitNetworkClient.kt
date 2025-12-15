package com.example.playlistmaker.network

import com.example.playlistmaker.creator.Storage
import com.example.playlistmaker.data.TracksSearchRequest
import com.example.playlistmaker.data.TracksSearchResponse
import com.example.playlistmaker.domain.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TracksSearchResponse {
        val searchList = storage.search((request as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}