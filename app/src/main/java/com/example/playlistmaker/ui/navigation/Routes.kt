package com.example.playlistmaker.ui.navigation

//object Routes {
//    const val MAIN = "main"
//    const val SEARCH = "search"
//    const val TRACK_DETAILS = "trackDetails"
//    const val SETTINGS = "settings"
//    const val PLAYLISTS = "playlists"
//    const val NEW_PLAYLIST = "newPlaylist"
//    const val FAVORITES = "favorites"
//    const val PLAYLIST = "playlist/{playlistId}"
//}

enum class Routes(
    val route: String
) {
    MAIN(
        route = "main"
    ),
    SEARCH(
        route = "search"
    ),
    TRACK_DETAILS(
        route = "trackDetails"
    ),
    SETTINGS(
        route = "settings"
    ),
    PLAYLISTS(
        route = "playlists"
    ),
    PLAYLIST(
        route = "playlist"
    ),
    NEW_PLAYLIST(
        route = "newPlaylist"
    ),
    FAVORITES(
        route = "favorites"
    );

    fun withArgs(vararg args: Long?): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}