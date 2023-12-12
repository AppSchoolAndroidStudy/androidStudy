package com.woojugoing.materialgallery.repo

import com.woojugoing.materialgallery.data.ImageItem

class ImageRepository {
    private val imageList = mutableListOf<ImageItem>()

    fun saveImage(imageItem: ImageItem) { imageList.add(imageItem) }
    fun getImageList(): List<ImageItem> { return imageList.toList() }
    fun clearImageList() { imageList.clear() }
}