package com.example.playlistmaker.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.themes.SecondaryGray
import com.example.playlistmaker.ui.components.ScreenHeader
import com.example.playlistmaker.ui.components.TrackListItem
import com.example.playlistmaker.ui.view_model.SearchState
import com.example.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
    ) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var textField by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        ScreenHeader(text = stringResource(R.string.search), onBackClick = {
            navController.popBackStack()
        })

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = textField,
            onValueChange = { textField = it },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SecondaryGray,
                focusedContainerColor = SecondaryGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = stringResource(R.string.search), style = MainTextStyle.copy(color = PrimaryGray))
            },
            prefix = {
                Image(
                    modifier = Modifier.padding(end = 8.dp).size(16.dp)
                        .clickable {
                        viewModel.search(textField)
                    },
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(PrimaryGray)
                )
            },
            trailingIcon = {
                if (textField.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { textField = "" },
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = PrimaryGray
                    )
                }
            }
        )
        when (screenState) {
            is SearchState.Initial -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Введите строку для поиска")
                }
            }
            is SearchState.Searching -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(15.dp)
                ) {
                    items(tracks.size) { index ->
                        TrackListItem(track = tracks[index])
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $error", color = Color.Red)
                }
            }
        }
    }
}