package com.example.playlistmaker.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.ui.themes.CommonTextStyle
import com.example.playlistmaker.ui.themes.PrimaryGray

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
    ) {
        stickyHeader {
            HorizontalDivider(
                thickness = 1.dp,
                color = PrimaryGray,
                modifier = Modifier
                    .padding(horizontal = 9.5.dp)
            )
        }
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 14.dp, end = 6.dp)
                        .size(16.dp),
                    tint = PrimaryGray
                )
                Text(text = historyList[index], style = CommonTextStyle)
            }
        }
    }
}