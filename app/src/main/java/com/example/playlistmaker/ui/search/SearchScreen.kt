package com.example.playlistmaker.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.Track
import com.example.playlistmaker.ui.navigation.ScreenHeader
import com.example.playlistmaker.ui.themes.MainTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray
import com.example.playlistmaker.ui.themes.SecondaryGray
import com.example.playlistmaker.ui.track.TrackListItem
import com.example.playlistmaker.ui.view_models.SearchState
import com.example.playlistmaker.ui.view_models.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier,
    searchViewModel: SearchViewModel,
    navigateBack: () -> Unit,
    navigateToTrackDetails: (Track?) -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    val historyList by searchViewModel.getHistoryList().collectAsState(initial = emptyList())
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }

            else -> Unit
        }
    }

    Column(
        modifier
    ) {
        ScreenHeader(
            text = stringResource(R.string.search),
            onBackClick = navigateBack
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SecondaryGray),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                shape = RoundedCornerShape(0.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        style = MainTextStyle.copy(color = PrimaryGray)
                    )
                },
                prefix = {
                    Image(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp),
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(PrimaryGray)
                    )
                },
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    text = ""
                                    searchViewModel.clearSearch()
                                },
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(R.string.clearr),
                            tint = PrimaryGray
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            if (isFocused && text.isEmpty() && historyList.isNotEmpty()) {
                HistoryRequests(
                    historyList = historyList,
                    onClick = { word ->
                        text = word
                    }
                )
            }
        }

        when (screenState) {
            is SearchState.Initial -> {
                if (text.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.search)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                if (tracks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.no_songs_found),
                            color = Color.Red
                        )
                    }
                } else {
                    LazyColumn {
                        items(tracks.size) { index ->
                            TrackListItem(
                                track = tracks[index],
                                onClick = { navigateToTrackDetails(tracks[index]) },
                                onLongClick = {}
                            )
                        }
                    }
                }
            }

            is SearchState.NoInternet -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 102.dp, bottom = 16.dp)
                            .width(120.dp)
                            .height(120.dp),
                        painter = painterResource(R.drawable.connection_trubles),
                        contentDescription = stringResource(R.string.connection_troubles)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 24.dp),
                        text = stringResource(R.string.connection_troubles),
                        textAlign = TextAlign.Center,
                        style = MainTextStyle
                    )
                    Text(
                        text = stringResource(R.string.load_is_failed),
                        textAlign = TextAlign.Center,
                        style = MainTextStyle
                    )
                }
            }

            is SearchState.EmptyResult -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 102.dp, bottom = 16.dp)
                            .width(120.dp)
                            .height(120.dp),
                        painter = painterResource(R.drawable.track_is_not_found),
                        contentDescription = stringResource(R.string.nothing_is_found)
                    )
                    Text(
                        text = stringResource(R.string.nothing_is_found),
                        style = MainTextStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(R.string.error),
                            color = Color.Red
                        )
                        Text(
                            error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
