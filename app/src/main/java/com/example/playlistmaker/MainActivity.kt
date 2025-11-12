package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenu()
        }
    }
}


@Composable
@Preview(name = "portrait")
fun MainMenu() {
    val context = LocalContext.current

    val intentSearch = Intent(context, SearchActivity::class.java)
    val intentPlaylists = Intent(context, PlaylistsActivity::class.java)
    val intentFavourite = Intent(context, FavouriteActivity::class.java)
    val intentSettings = Intent(context, SettingsActivity::class.java)

    Box(
        modifier = Modifier
            .background(Color(55, 114, 231))
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 28.dp,
                            end = 16.dp
                        )
                ) {
                    MenuButton({ RowSearch() }, intentSearch)

                    MenuButton({ RowPlaylists() }, intentPlaylists)

                    MenuButton({ RowFavourite() }, intentFavourite)

                    MenuButton({ RowSettings() }, intentSettings)
                }
            }
        }
    }
}

@Composable
fun MenuButton(content: @Composable RowScope.() -> Unit, intent: Intent) {
    val context = LocalContext.current

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .width(328.dp)
            .height(66.dp),
        onClick = { context.startActivity(intent) },
        enabled = true,
        content = content,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun RowSearch() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.search),
            contentDescription = null
        )
        Text(
            stringResource(R.string.search),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.ys_medium))
            )
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}

@Composable
fun RowPlaylists() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.playlist),
            contentDescription = null
        )
        Text(
            stringResource(R.string.playlists),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.ys_medium))
            ),
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}

@Composable
fun RowFavourite() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.heart),
            contentDescription = null
        )
        Text(
            stringResource(R.string.favourite),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.ys_medium))
            ),
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}

@Composable
fun RowSettings() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.settings),
            contentDescription = null
        )
        Text(
            stringResource(R.string.settings),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.ys_medium))
            ),
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}