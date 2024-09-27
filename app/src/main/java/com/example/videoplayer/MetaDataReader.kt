package com.example.videoplayer

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

data class MetaData(
    val fileName: String
)

interface MetaDataReader {
    fun getMetaDataReaderFromUri(contentUris: Uri): MetaData?
}

class MetaDataReaderImpl(
    private val app: Application
) : MetaDataReader {
    @SuppressLint("Recycle")
    override fun getMetaDataReaderFromUri(contentUris: Uri): MetaData? {
        if (contentUris.scheme != "content") return null
        val fileName = app.contentResolver.query(
            contentUris,
            arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(index)
        }
        return fileName?.let { fullfileName ->
            MetaData(
                fileName = Uri.parse(fullfileName).lastPathSegment ?: return null
            )
        }
    }

}