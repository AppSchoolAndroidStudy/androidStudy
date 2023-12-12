package com.woojugoing.materialgallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.woojugoing.materialgallery.adapter.ImageAdapter
import com.woojugoing.materialgallery.vm.MainViewModel
import com.woojugoing.materialgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var albumLauncher: ActivityResultLauncher<Intent>
    private lateinit var mainViewModel: MainViewModel
    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        requestPermissions(permissionList, 0)

        val galleryContracts = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(galleryContracts) { result ->
            if (result.resultCode == RESULT_OK) result.data?.data?.let { uri -> handleSelectedImage(uri) }
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModel()
        setupUI()
    }

    private fun handleSelectedImage(uri: Uri) {
        mainViewModel.processImage(uri)
    }

    private fun observeViewModel() {
        mainViewModel.imageList.observe(this) { updateUI() }
    }

    private fun updateUI() {}

    private fun setupUI() {
        activityMainBinding.run {
            val sheetBehavior = BottomSheetBehavior.from(include1.bottomSheet)
            sheetBehavior.isHideable = false
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            toolbarMain.run {
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_main_add -> {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            intent.type = "image/*"
                            val mimeType = arrayOf("image/*")
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                            albumLauncher.launch(intent)
                        }
                        R.id.menu_item_delete -> showDeleteConfirmationDialog()
                    }
                    false
                }
            }

            recyclerMain.run {
                adapter = ImageAdapter { imageItem -> mainViewModel.onImageItemSelected() }
                layoutManager = CarouselLayoutManager()
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.run {
            setMessage("현재 표시 중인 사진을 전부 삭제하시겠습니까?")
            setPositiveButton("예") { _, _ -> mainViewModel.clearImageList() }
            setNegativeButton("아니오", null)
            show()
        }
    }
}