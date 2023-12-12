package com.woojugoing.materialgallery

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.woojugoing.materialgallery.databinding.ActivityMainBinding
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.woojugoing.materialgallery.databinding.RowBinding

class _MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var albumLauncher: ActivityResultLauncher<Intent>
    val imageList = mutableListOf<Bitmap>()
    var exif: ExifInterface? = null
    @RequiresApi(Build.VERSION_CODES.Q)
    val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION)


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        requestPermissions(permissionList, 0)

        val galleryContracts = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(galleryContracts){
            if(it.resultCode == RESULT_OK) {
                val uri = it.data?.data
                if(uri != null) {
                    val source = ImageDecoder.createSource(contentResolver, uri)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    val targetWeight = 2048
                    val ratio = targetWeight.toDouble() / bitmap.width
                    val targetHeight = (bitmap.height * ratio).toInt()
                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWeight, targetHeight, false)
                    activityMainBinding.run{
                        imageView.setImageBitmap(scaledBitmap)
                        imageList.add(scaledBitmap)
                        include1.run {
                            val placeLat = getImageLocation(uri)?.latitude
                            val placeLng = getImageLocation(uri)?.longitude
                            val fileName = getFileNameFromUri(uri)
                            val photoDate = fileName.replace("[^0-9]".toRegex(), "")
                            buttonDialog.setOnClickListener {
                                val builder = MaterialAlertDialogBuilder(this@_MainActivity)
                                builder.run {
                                    setMessage("""
                                        $fileName

                                        ${split(photoDate)}

                                        ${getAbsolutePathFromUri(uri)}
                                    """.trimIndent())
                                    show()
                                }
                            }
                            buttonPlace.setOnClickListener {
                                if(placeLat != null) {
                                    val streetUri = Uri.parse("google.streetview:cbll=${placeLat},${placeLng}")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, streetUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    startActivity(mapIntent)
                                } else {
                                    Snackbar.make(activityMainBinding.root, "Not available Place Data", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                        recyclerMain.run {
                            adapter = RecyclerViewAdapter()
                            layoutManager = CarouselLayoutManager()
                        }
                    }
                }
            }
        }

        activityMainBinding.run {
            val sheetBehavior = BottomSheetBehavior.from(include1.bottomSheet)
            sheetBehavior.isHideable = false
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            toolbarMain.run {
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.menu_main_add -> {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            intent.type = "image/*"
                            val mimeType = arrayOf("image/*")
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                            albumLauncher.launch(intent)
                        }
                        R.id.menu_item_delete -> {
                            var builder = MaterialAlertDialogBuilder(this@_MainActivity)
                            builder.run {
                                setMessage("보고 있는 사진들을 전부 삭제하실건가요?")
                                setPositiveButton("예"){ _: DialogInterface, _: Int ->
                                    imageList.clear()
                                    recyclerMain.adapter?.notifyDataSetChanged()
                                    imageView.setImageResource(R.drawable.ic_launcher_foreground)
                                }
                                setNegativeButton("아뇨", null)
                                show()
                            }
                        }
                    }
                    false
                }
            }
        }
    }

    // Exif Data를 통하여 날짜를 가져오는 것보다,
    // 일반적으로 안드로이드에서 가져오거나 저장하는 사진들은 파일 이름에 다른 내용이 아닌 시간에 관한 정보만 기입하기에,
    // Exif Data가 존재하지 않더라도 쉽게 날짜 정보를 Date Format으로 가져오는 것이 가능함.
    private fun split(date: String): String {
        val year = date.substring(0 until 4)
        val month = date.substring(4, 6)
        val day = date.substring(6, 8)
        val hour = date.substring(8, 10)
        val minute = date.substring(10, 12)
        val second = date.substring(12, 14)

        return "${year}/${month}/${day} ${hour}:${minute}:${second}"
    }

    private fun getFileNameFromUri(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        } ?: "Unknown"
    }

    private fun getImageLocation(uri: Uri): Location? {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.let {
            exif = ExifInterface(inputStream)
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

    private fun getAbsolutePathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: "경로를 찾을 수 없음"
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){

            var carouselImageView: ImageView

            init {
                carouselImageView = rowBinding.carouselImageView
                rowBinding.root.setOnClickListener {
                    val selectedBitmap = imageList[adapterPosition]
                    activityMainBinding.imageView.setImageBitmap(selectedBitmap)
                    activityMainBinding.include1.run {

                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = RowBinding.inflate(layoutInflater)
            return ViewHolderClass(rowMainBinding)
        }

        override fun getItemCount(): Int {
            return imageList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.carouselImageView.setImageBitmap(imageList[position])
        }
    }
}