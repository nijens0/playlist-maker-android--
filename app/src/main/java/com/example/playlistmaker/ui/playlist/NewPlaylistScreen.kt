package com.example.playlistmaker.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.themes.AccentBlue
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray


@Composable
fun NewPlaylistScreen(
    modifier: Modifier,
    navigateBack: () -> Unit
) {
    Column(
        modifier
    ) {
        ScreenHeader(text = stringResource(R.string.new_playlist)) { navigateBack }

        Image(
            modifier = Modifier
                .size(312.dp)
                .padding(horizontal = 24.dp),
            painter = painterResource(R.drawable.playlist),
            contentDescription = stringResource(R.string.playlist_image)
        )
        OutlinedTextField (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = "",
            onValueChange = {}
        )
    }
}


@Preview
@Composable
fun NewPlaylistScreen(
) {
    var textStateOfName by remember { mutableStateOf("") }
    var textStateOfDescription by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(Color.White)
    ) {
        ScreenHeader(text = stringResource(R.string.new_playlist)) {  }

        Image(
            modifier = Modifier
                .padding(vertical = 138.dp)
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.add_photo),
            contentDescription = stringResource(R.string.playlist_image),
        )
        OutlinedTextField (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = textStateOfName,
            onValueChange = {

            },
            placeholder = {
                Text(
                    text = "${stringResource(R.string.name)}*",
                    style = CommonTextStyle,
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = textStateOfDescription,
            onValueChange = {},
            placeholder = {
                Text(
                    text = stringResource(R.string.description),
                    style = CommonTextStyle,
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1
        )
        Spacer(
            modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
        )
        Button(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    end = 17.dp,
                    bottom = 32.dp,
                    start = 17.dp
                )
                .fillMaxWidth()
                .height(44.dp),
            onClick = {},
            content = {
                Text(
                    text = stringResource(R.string.create),
                    style = MainTextStyle.copy(color = Color.White)
                )
            },
            enabled = false,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = PrimaryGray,
                contentColor = AccentBlue
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}