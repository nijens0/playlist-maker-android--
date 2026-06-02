package com.example.playlistmaker.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {
    private val preferencesKey = stringPreferencesKey("history_key")

    companion object {
        const val MAX_ENTRIES = 10
        const val SEPARATOR = ","
    }

    fun addEntry(word: String) {
        if (word.isEmpty()) return

        coroutineScope.launch {
            dataStore.updateData { preferences ->
                val historyString = preferences[preferencesKey].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val subList = history.take(MAX_ENTRIES)
                val updatedString = subList.joinToString(SEPARATOR)


                preferences.toMutablePreferences().apply {
                    this[preferencesKey] = updatedString
                }

            }
        }
    }

    fun getEntries(): Flow<List<String>> =
        dataStore.data.map { pref: Preferences ->
            val string = pref[preferencesKey]
            string?.split(SEPARATOR)?.toMutableList() ?: emptyList()
        }

}
