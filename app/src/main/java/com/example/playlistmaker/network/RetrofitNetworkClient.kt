package com.example.playlistmaker.network

import com.example.playlistmaker.data.BaseResponse
import com.example.playlistmaker.data.TracksSearchRequest
import com.example.playlistmaker.domain.NetworkClient
import okio.IOException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {

    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when (dto) {

                is TracksSearchRequest -> {
                    val response = api.searchTracks(
                        query = dto.expression,
                        media = "music",
                        entity = "song",
                        limit = 10
                    )
                    response.resultCode = 200
                    return response
                }
                else -> BaseResponse().apply {
                    resultCode = 400
                    errorMessage = "Invalid request type: expected TracksSearchRequest or String"
                }
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Network error: ${e.message ?: "Unknown IO error"}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
            }
        }
    }
}