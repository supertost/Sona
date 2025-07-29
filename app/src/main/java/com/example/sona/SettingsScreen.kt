package com.example.sona

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun SettingsScreen(navController: NavController, versionName: String) {

    val darkColors = darkColorScheme(
        background = Color.Black,
        surface = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
        primary = Color.DarkGray,
        onPrimary = Color.White
    )

    var selectedTab by remember { mutableStateOf("About") }

    MaterialTheme(colorScheme = darkColors) {

        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

            val contextBack = LocalContext.current
            val controller = remember { SpotifySessionController(contextBack) }
            val albumArt = controller.albumArt

            if (albumArt != null) {
                Image(
                    bitmap = albumArt.asImageBitmap(),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize().blur(100.dp).alpha(0.2f),
                    contentScale = ContentScale.Crop
                )
            }

            Column (modifier = Modifier.fillMaxSize().padding(start = 30.dp).padding(30.dp)) {

                Text("Settings", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)

                Spacer(modifier = Modifier.height(40.dp))

                Row (modifier = Modifier.fillMaxSize()) {

                    Column (modifier = Modifier.fillMaxHeight().width(200.dp).padding(end = 20.dp)) {

                        listOf("About", "Permissions","Appearance").forEach { tab ->
                            Button(onClick = { selectedTab = tab }, modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp), colors = ButtonDefaults.buttonColors(containerColor = if (selectedTab == tab) Color.DarkGray.copy(alpha = 0.5f) else Color.Transparent, contentColor = Color.White), shape = RoundedCornerShape(10.dp)) {
                                Text(tab)
                            }
                        }
                    }



                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 100.dp)
                    ) {
                        when (selectedTab) {
                            "About" -> AboutSettings(versionName)
                            "Permissions" -> PermissionSettings()
                            "Appearance" -> AppearanceSettings()
                        }
                    }

                }

            }




            Button(
                modifier = Modifier.align(Alignment.TopEnd).padding(30.dp),
                onClick = { navController.navigate("player"){popUpTo("player"){inclusive = true}} },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    contentColor = Color.Unspecified
                )
            ) { Text("< Player") }
        }

    }
}

@Composable
fun AboutSettings(versionName: String) {

    Column (modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {

        Row (modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.sona_icon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .height(100.dp)
                    .padding(end = 20.dp)
            )

            Column {

                Text("Sona", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)

                Text("Sona is an app that controls Spotify playback without the need for the Spotify Web API", color = Color.White)

                Spacer(modifier = Modifier.height(40.dp))
            }

        }

        val uriHandler = LocalUriHandler.current

        val annotatedText = buildAnnotatedString {
            append("Made By Emir Dilekci - GitHub: ")

            pushStringAnnotation(
                tag = "URL",
                annotation = "https://github.com/supertost"
            )
            withStyle(style = SpanStyle(
                color = Color.Cyan,
                textDecoration = TextDecoration.Underline
            )
            ) {
                append("github.com/supertost")
            }
            pop()
        }

        ClickableText(
            text = annotatedText,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item)
                    }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("This app isn't affiliated with or endorsed by Spotify in any way.", color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text("Version: $versionName", color = Color.White)

    }


}

@Composable
fun AppearanceSettings() {
    Text("Not yet implemented", color = Color.White)
}

@Composable
fun PermissionSettings() {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        context.startActivity(intent)
    }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray.copy(alpha = 0.5f)), shape = RoundedCornerShape(10.dp)) {
        Text("Fix Media Control Permissions")
    }
}