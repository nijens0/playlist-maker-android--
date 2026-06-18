package com.example.playlistmaker.ui.playlist

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.navigation.ScreenHeader
import com.example.playlistmaker.ui.themes.AccentBlue
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.view_models.NewPlaylistViewModel


@Composable
fun NewPlaylistScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    newPlaylistViewModel: NewPlaylistViewModel
) {
    var textStateOfName by remember { mutableStateOf("") }
    var textStateOfDescription by remember { mutableStateOf("") }
    var isNameFilled by remember { mutableStateOf(false) }

    val coverImageUri by newPlaylistViewModel.coverImageUri.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            newPlaylistViewModel.setCoverImageUri(it.toString())
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }

    Column(
        modifier
    ) {
        ScreenHeader(
            text = stringResource(R.string.new_playlist),
            onBackClick = navigateBack
        )

        Box(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(312.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        imagePickerLauncher.launch("image/*")
                    } else {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                imagePickerLauncher.launch("image/*")
                            }

                            else -> {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                }
        ) {
            AsyncImage(
                model = coverImageUri,
                contentDescription = stringResource(R.string.playlist_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.add_photo),
                placeholder = painterResource(R.drawable.add_photo)
            )
        }

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
                newPlaylistViewModel.createNewPlaylist(textStateOfName, textStateOfDescription)
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
