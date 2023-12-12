package com.woojugoing.materialgallery.vm

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Location
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.woojugoing.materialgallery.data.ImageItem
import com.woojugoing.materialgallery.data.ImageItem.Companion.extractDateFromFileName
import com.woojugoing.materialgallery.repo.ImageRepository
import kotlinx.coroutines.launch
import java.io.InputStream

class MainViewModel(application: Application, private val imageRepository: ImageRepository) : AndroidViewModel(application) {

    private val _imageList = MutableLiveData<List<ImageItem>>()
    val imageList: LiveData<List<ImageItem>> get() = _imageList

    private var exif: ExifInterface? = null

    fun processImage(uri: Uri) {
        viewModelScope.launch {
            val processedImage = process(uri)
            imageRepository.saveImage(processedImage)
            _imageList.value = imageRepository.getImageList()
        }
    }

    private fun process(uri: Uri): ImageItem {
        val contentResolver = getApplication<Application>().contentResolver
        val source = ImageDecoder.createSource(contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)

        val targetWidth = 2048
        val ratio = targetWidth.toDouble() / bitmap.width
        val targetHeight = (bitmap.height * ratio).toInt()
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

        val fileName = getFileNameFromUri(uri)
        val photoDate = extractDateFromFileName(fileName)
        val location = getImageLocation(uri)

        return ImageItem(resizedBitmap, fileName, split(photoDate), location)
    }

    fun clearImageList() {
        viewModelScope.launch {
            imageRepository.clearImageList()
            _imageList.value = emptyList()
        }
    }

    fun onImageItemSelected() {}

    private fun split(date: String): String {
        val year = date.substring(0 until 4)
        val month = date.substring(4, 6)
        val day = date.substring(6, 8)
        val hour = date.substring(8, 10)
        val minute = date.substring(10, 12)
        val second = date.substring(12, 14)

        return "$year/$month/$day $hour:$minute:$second"
    }

    private fun getFileNameFromUri(uri: Uri): String {
        val contentResolver = getApplication<Application>().contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        } ?: "Unknown"
    }

    private fun getImageLocation(uri: Uri): Location? {
        val contentResolver = getApplication<Application>().contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        inputStream?.let {
            exif = ExifInterface(it)
        }

        exif?.let {
            val latLong = FloatArray(2)
            if (it.getLatLong(latLong)) {
                val location = Location("")
                location.latitude = latLong[0].toDouble()
                location.longitude = latLong[1].toDouble()
                return location
            }
        }
        return null
    }
}