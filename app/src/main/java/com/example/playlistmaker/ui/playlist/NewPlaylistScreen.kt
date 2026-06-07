package com.example.playlistmaker.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.navigation.ScreenHeader
import com.example.playlistmaker.ui.themes.AccentBlue
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.view_models.PlaylistsViewModel


@Composable
fun NewPlaylistScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    playlistsViewModel: PlaylistsViewModel
) {
    var textStateOfName by remember { mutableStateOf("") }
    var textStateOfDescription by remember { mutableStateOf("") }
    var isNameFilled by remember { mutableStateOf(false) }

    Column(
        modifier
    ) {
        ScreenHeader(
            text = stringResource(R.string.new_playlist),
            onBackClick = navigateBack
        )

        Image(
            modifier = Modifier
                .padding(vertical = 138.dp)
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.add_photo),
            contentDescription = stringResource(R.string.playlist_image),
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = textStateOfName,
            label = {
                Text(
                    text = "${stringResource(R.string.name)}*"
                )
            },
            onValueChange = {
                textStateOfName = it
                isNameFilled = textStateOfName.isNotBlank()
            },
            placeholder = {
                Text(
                    text = "${stringResource(R.string.name)}*",
                    style = CommonTextStyle,
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentBlue
            )
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = textStateOfDescription,
            label = {
                Text(
                    text = stringResource(R.string.description)
                )
            },
            onValueChange = {
                textStateOfDescription = it
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.description),
                    style = CommonTextStyle,
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentBlue
            )
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
            onClick = {
                playlistsViewModel.createNewPlaylist(textStateOfName, textStateOfDescription)
                navigateBack()
            },
            content = {
                Text(
                    text = stringResource(R.string.create),
                    style = MainTextStyle.copy(color = Color.White)
                )
            },
            enabled = isNameFilled,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = PrimaryGray,
                containerColor = if (isNameFilled) AccentBlue else PrimaryGray
            ),
            shape = RoundedCornerShape(8.dp)
            )
    }
}
