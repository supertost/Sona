<h1 align="center">Sona</h1>

<p align="center">
  <img src="https://github.com/supertost/Sona/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.webp" alt="App Icon" width="130"/>
</p>

<p align="center">A Spotify media controller built with Kotlin and Jetpack Compose for Android devices that does not need the Spotify Web API to work.</p>

---

## What it does

- Play/Pause music playing on Spotify
- Skip or go to the previous song
- A button on the bottom-left to open Spotify and add the playing song to your like list, add new songs to queue, or change what's playing
- A button on the bottom-right corner to refresh the content in case it does not update automatically

It works nicely with Spotify Connect, allowing you to listen to your music from other devices while controlling playback from this app.

## Requirements
- As this app does not use the Spotify Web API, the Spotify app has to be downloaded and open in the background for this app to function properly
- Requires Android 12+ (API 31 and above).

## Program in action

| Skip tracks | Play/Pause with a nice animation | Open Spotify to do more actions |
| ------------- | ------------- | ------------- |
| <video src="https://github.com/user-attachments/assets/1bcba7e0-340a-4b4d-8971-1a9fdc833705" alt="exclusion video"></video> | <video src="https://github.com/user-attachments/assets/e728902e-67c7-4b69-8195-eb5c23653ced" alt="no exclusion video"></video> | <video src="https://github.com/user-attachments/assets/d72fa620-57bb-4f6e-be81-6fbaeb2457ec" alt="no exclusion video"></video>  |
<p align="center">
<b><br>Sona is also optimised for Android tablets as well</b> <br><br>
<img src="https://github.com/user-attachments/assets/6cfcb8dd-febd-4fca-a6e1-a385bc2faf0e" width="600"/>
</p>

## Installation

> The APK file isn’t available in the releases tab — I’m still deciding whether to release it on Google Play or here under the Releases tab. If I choose to release it on GitHub, the installation steps will look like the ones below.

1. Download the APK file from the Releases tab and install it on your Android Device

2. After installing, allow notification access from the settings page as shown in the video below

<video src="https://github.com/user-attachments/assets/3535b32a-fb3e-45a5-a024-3285ef26f339" alt="no exclusion video"></video>

---

This app is not affiliated with or endorsed by Spotify in any way.

It is a small project I made to get familiar with Kotlin and Jetpack Compose

Sona does not use the Spotify Web API, it reads the song metadata from Android's media session and notification system.

The album artwork shown in the demo videos belongs to the respective artists. I do not claim ownership.
