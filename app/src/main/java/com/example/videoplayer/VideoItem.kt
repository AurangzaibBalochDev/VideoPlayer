package com.example.videoplayer

import android.net.Uri

data class VideoItem(
    val contentUri: Uri,
    val mediaItem: androidx.media3.common.MediaItem,
    val name: String,
)