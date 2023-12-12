package com.woojugoing.materialgallery.data

import android.graphics.Bitmap
import android.location.Location

data class ImageItem(
    val bitmap: Bitmap,
    val fileName: String,
    val date: String,
    val location: Location?
) {
    companion object {
        fun extractDateFromFileName(fileName: String): String {
            return fileName.replace("[^0-9]".toRegex(), "")
        }
    }
}