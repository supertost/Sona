package com.example.sona

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(navController: NavController, isPlaying: Boolean) {

    val context = LocalContext.current
    val controller = remember { SpotifySessionController(context) }

    val track = controller.track ?: "No Song Information Available"
    val artist = controller.artist ?: "No Artist Information Available"
    val albumArt = controller.albumArt

    //val isPlaying = controller.isPlaying

    val albumArtScale by animateFloatAsState(
        targetValue = if (isPlaying) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "AlbumArtScale"
    )

    val darkColors = darkColorScheme(
        background = Color.Black,
        surface = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
        primary = Color.DarkGray,
        onPrimary = Color.White
    )


    LaunchedEffect(Unit) {
        while (true) {
            controller.updatePlaybackPosition()
            delay(1000)
        }
    }

    val progress by remember(controller.currentPosition, controller.duration) {
        derivedStateOf {
            if (controller.duration > 0) {
                controller.currentPosition.toFloat() / controller.duration
            } else {
                0f
            }
        }
    }


    MaterialTheme(colorScheme = darkColors) {

        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

            if (albumArt != null) {
                Image(
                    bitmap = albumArt.asImageBitmap(),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize().blur(90.dp),
                    contentScale = ContentScale.Crop
                )
            }
            else {
                Image(
                    painter = painterResource(id = R.drawable.placeholdderalbumart),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize().blur(90.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

            Box(modifier = Modifier.fillMaxSize().padding(30.dp)) {

                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        if (albumArt != null) {
                            Image(
                                bitmap = albumArt.asImageBitmap(), contentDescription = "Album Art",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .scale(albumArtScale)
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(id = R.drawable.placeholdderalbumart),
                                contentDescription = "Album Art",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                track,
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(artist, color = Color.White)

                            Spacer(modifier = Modifier.height(20.dp))


                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier.fillMaxWidth().height(5.dp),
                                color = Color.White.copy(alpha = 0.5f),
                                trackColor = Color.DarkGray.copy(alpha = 0.5f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))


                            Row() {

                                Button(
                                    modifier = Modifier.padding(0.dp),
                                    onClick = { controller.previous() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Unspecified
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.previous),
                                        contentDescription = "PreviousButton",
                                        modifier = Modifier.height(60.dp)
                                    )
                                }
                                Button(
                                    modifier = Modifier.padding(0.dp),
                                    onClick = { controller.togglePlayPause() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Unspecified
                                    )
                                ) {

                                    AnimatedContent(
                                        targetState = isPlaying,
                                        transitionSpec = {
                                            fadeIn(tween(200)) + scaleIn(tween(200), initialScale = 0.2f) togetherWith
                                                    fadeOut(tween(200)) + scaleOut(tween(200), targetScale = 0.2f)
                                        },
                                        label = "PlayPauseTransition"
                                    ) { playing ->
                                        val icon = if (playing) R.drawable.pause else R.drawable.play
                                        Image(
                                            painter = painterResource(id = icon),
                                            contentDescription = if (playing) "Pause" else "Play",
                                            modifier = Modifier.height(60.dp)
                                        )
                                    }
                                }
                                Button(
                                    modifier = Modifier.padding(0.dp),
                                    onClick = { controller.next() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Unspecified
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.next),
                                        contentDescription = "next Button",
                                        modifier = Modifier.height(60.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { navController.navigate("settings") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Unspecified
                    )
                ) { Image(
                    painter = painterResource(id = R.drawable.settingsicon),
                    contentDescription = "Settings Button",
                    modifier = Modifier.height(20.dp).alpha(0.3f)
                ) }


                Button(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { navController.navigate("player") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Unspecified
                    )
                ) {/* Image(
                    painter = painterResource(id = R.drawable.settingsicon),
                    contentDescription = "Refresh Button",
                    modifier = Modifier.height(20.dp).alpha(0.3f)
                ) */}

                Button(
                    onClick = { openSpotify(context) },
                    modifier = Modifier.align(Alignment.BottomStart),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    /*Text("Open Spotify")*/
                }

            }
        }
        }
}


fun openSpotify(context: Context) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage("com.spotify.music")
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Fallback: Open in browser if Spotify not installed
        val webIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://open.spotify.com/")
        }
        context.startActivity(webIntent)
    }
}