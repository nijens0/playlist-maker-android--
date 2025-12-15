package com.example.playlistmaker.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.themes.AccentBlue
import com.example.playlistmaker.ui.themes.HeaderStyle
import com.example.playlistmaker.ui.components.MenuButton
import com.example.playlistmaker.ui.navigation.Routes

@Composable
fun MainMenuScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(AccentBlue)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                stringResource(R.string.PlaylistHeader),
                modifier = Modifier
                    .padding(start = 16.dp, top = 14.dp, bottom = 30.dp)
                    .width(140.dp)
                    .height(26.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.ys_medium))
                )
            )
            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp, start = 28.dp, end = 16.dp)
                ) {
                    MenuButton(onClick = { navController.navigate(Routes.SEARCH) }) { RowSearch() }
                    MenuButton(onClick = { navController.navigate(Routes.PLAYLISTS) }) { RowPlaylists() }
                    MenuButton(onClick = { navController.navigate(Routes.FAVORITES) }) { RowFavourite() }
                    MenuButton(onClick = { navController.navigate(Routes.SETTINGS) }) { RowSettings() }
                }
            }
        }
    }
}

@Composable
fun RowSearch() {
    MenuRowItem(
        iconResId = R.drawable.search,
        text = stringResource(R.string.search)
    )
}

@Composable
fun RowPlaylists() {
    MenuRowItem(
        iconResId = R.drawable.playlist,
        text = stringResource(R.string.playlists)
    )
}

@Composable
fun RowFavourite() {
    MenuRowItem(
        iconResId = R.drawable.heart,
        text = stringResource(R.string.favourite)
    )
}

@Composable
fun RowSettings() {
    MenuRowItem(
        iconResId = R.drawable.settings,
        text = stringResource(R.string.settings)
    )
}

@Composable
fun MenuRowItem(iconResId: Int, text: String) {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(iconResId), contentDescription = null)
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            text = text,
            style = HeaderStyle
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}