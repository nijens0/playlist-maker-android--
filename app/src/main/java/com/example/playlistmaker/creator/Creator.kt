package com.example.playlistmaker.creator

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.playlistmaker.data.database.AppDatabase
import com.example.playlistmaker.data.database.daos.PlaylistsDao
import com.example.playlistmaker.data.database.daos.TracksDao
import com.example.playlistmaker.data.database.repositories.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.database.repositories.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.database.repositories.TracksRepositoryImpl
import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
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

    private val Context.dataStore by preferencesDataStore(name = "history_search")
    private var tracksRepository: TracksRepository? = null
    private var playlistsRepository: PlaylistsRepository? = null
    private var searchHistoryRepository: SearchHistoryRepository? = null
    private var appDatabase: AppDatabase? = null
    private var tracksDao: TracksDao? = null
    private var playlistsDao: PlaylistsDao? = null

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

    fun getTracksRepository(context: Context): TracksRepository {
        if (tracksRepository == null) {
            val database = getAppDataBase(context)
            tracksRepository = TracksRepositoryImpl(
                tracksDao = getTracksDao(database),
                playlistsDao = getPlaylistsDao(database),
                networkClient = getRetrofitNetworkClient()
            )
        }
        return tracksRepository!!
    }

    fun getPlaylistsRepository(context: Context): PlaylistsRepository {
        if (playlistsRepository == null) {
            val database = getAppDataBase(context)
            playlistsRepository = PlaylistsRepositoryImpl(context, getPlaylistsDao(database))
        }
        return playlistsRepository!!
    }

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        if (searchHistoryRepository == null) {
            searchHistoryRepository = SearchHistoryRepositoryImpl(
                searchHistoryPreferences = SearchHistoryPreferences(context.applicationContext.dataStore)
            )
        }
        return searchHistoryRepository!!
    }

    fun getAppDataBase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context = context.applicationContext,
                klass = AppDatabase::class.java,
                name = "playlists_maker"
            ).build()
        }
        return appDatabase!!
    }

    fun getTracksDao(database: AppDatabase?): TracksDao {
        if (tracksDao == null) {
            tracksDao = database?.TracksDao()
        }
        return tracksDao!!
    }

    fun getPlaylistsDao(database: AppDatabase?): PlaylistsDao {
        if (playlistsDao == null) {
            playlistsDao = database?.PlaylistsDao()
        }
        return playlistsDao!!
    }
}