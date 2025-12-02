package com.example.playlistmaker.ui.settings

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.playlistmaker.R
import com.example.playlistmaker.themes.MainTextStyle
import com.example.playlistmaker.themes.PrimaryGray
import com.example.playlistmaker.themes.SecondaryGray
import com.example.playlistmaker.ui.components.MenuButton
import com.example.playlistmaker.ui.components.ScreenHeader

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ScreenHeader(text = stringResource(R.string.settings), onBackClick = {
            navController.popBackStack()
        })

        Spacer(modifier = Modifier.height(20.dp))

        MenuButton(onClick = { /* TODO: Theme switch logic */ }) { RowDarkTheme() }

        MenuButton(onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, context.getString(R.string.shareAppMessage))
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, null))
        }) { RowShareApp() }

        MenuButton(onClick = {
            val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.myEmail)))
                putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.emailSubject))
                putExtra(Intent.EXTRA_TEXT, context.getString(R.string.emailMessage))
            }
            try {
                context.startActivity(supportIntent)
            } catch (e: Exception) {
            }
        }) { RowTextToSupport() }

        MenuButton(onClick = {
            val agreementIntent =
                Intent(Intent.ACTION_VIEW, context.getString(R.string.practicumOffer).toUri())
            context.startActivity(agreementIntent)
        }) { RowUserAgreement() }
    }
}

@Composable
fun RowDarkTheme() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = stringResource(R.string.darkTheme),
            style = MainTextStyle
        )
        Switch(
            modifier = Modifier.padding(end = 12.dp),
            checked = false,
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = PrimaryGray, uncheckedTrackColor = SecondaryGray,
                uncheckedBorderColor = Color.Transparent, checkedThumbColor = PrimaryGray,
                checkedTrackColor = SecondaryGray
            )
        )
    }
}

@Composable
fun RowShareApp() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = stringResource(R.string.shareApp),
            style = MainTextStyle
        )
        Icon(
            modifier = Modifier.padding(end = 12.dp),
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = PrimaryGray
        )
    }
}

@Composable
fun RowTextToSupport() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = stringResource(R.string.textToSupport),
            style = MainTextStyle
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.support),
            contentDescription = null
        )
    }
}

@Composable
fun RowUserAgreement() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = stringResource(R.string.userAgreement),
            style = MainTextStyle
        )
        Image(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null
        )
    }
}