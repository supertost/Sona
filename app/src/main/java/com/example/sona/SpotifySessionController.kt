package com.example.sona

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SpotifySessionController(context: Context) {

    var track by mutableStateOf<String?>(null)
    var artist by mutableStateOf<String?>(null)
    var albumArt by mutableStateOf<Bitmap?>(null)

    var isPlaying by mutableStateOf(false)


    var currentPosition by mutableLongStateOf(0L)
    var duration by mutableLongStateOf(1L)


    private var spotifyController: MediaController? = null

    init {
        try {
            val sessionManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
            val componentName = ComponentName(context, NotificationCaptureService::class.java)
            val sessions = sessionManager.getActiveSessions(componentName)

            Log.d("SpotifyNative", "Found ${sessions.size} active sessions")

            for (controller in sessions) {
                Log.d("SpotifyNative", "Session: ${controller.packageName}")
                if (controller.packageName.contains("spotify")) {
                    spotifyController = controller
                    extractMetadata(controller.metadata)
                    Log.d("SpotifyNative", "Spotify session attached")

                    spotifyController?.registerCallback(object : MediaController.Callback() {
                        override fun onMetadataChanged(metadata: MediaMetadata?) {
                            Log.d("SpotifyNative", "Metadata changed")
                            extractMetadata(metadata)
                        }

                        override fun onPlaybackStateChanged(state: PlaybackState?) {
                            Log.d("SpotifyNative", "Playback state changed: ${state?.state}")
                            isPlaying = state?.state == PlaybackState.STATE_PLAYING
                        }

                    })

                    break
                }
            }

            if (spotifyController == null) {
                Log.e("SpotifyNative", "No Spotify session found")
            }

        } catch (e: SecurityException) {
            Log.e("SpotifyNative", "SecurityException: ${e.message}")
        }



        spotifyController?.registerCallback(object : MediaController.Callback() {
            override fun onMetadataChanged(metadata: MediaMetadata?) {
                extractMetadata(metadata)
            }

            override fun onPlaybackStateChanged(state: PlaybackState?) {
                currentPosition = state?.position ?: 0L
                Log.d("SpotifyNative", "Playback position: $currentPosition")
            }
        })
    }




    private fun extractMetadata(metadata: MediaMetadata?) {
        metadata?.let {
            track = it.getString(MediaMetadata.METADATA_KEY_TITLE)
            artist = it.getString(MediaMetadata.METADATA_KEY_ARTIST)
            albumArt = it.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
            duration = it.getLong(MediaMetadata.METADATA_KEY_DURATION)
            Log.d("SpotifyNative", "Track: $track - $artist")
        }
    }

    fun togglePlayPause() {
        spotifyController?.transportControls?.let {
            val state = spotifyController?.playbackState?.state
            if (state == PlaybackState.STATE_PLAYING) {
                it.pause()
            } else {
                it.play()
            }
        }
    }

    fun next() = spotifyController?.transportControls?.skipToNext()
    fun previous() = spotifyController?.transportControls?.skipToPrevious()


    fun updatePlaybackPosition() {
        currentPosition = spotifyController?.playbackState?.position ?: 0L
    }
}
