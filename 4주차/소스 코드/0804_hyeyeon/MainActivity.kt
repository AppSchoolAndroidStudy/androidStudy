package com.example.multiimage

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.multiimage.databinding.ActivityMainBinding
import com.example.multiimage.databinding.RowBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var rowBinding: RowBinding

    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    var imageList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        rowBinding = RowBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        albumLauncher = albumSetting()

        activityMainBinding.run {
            toolbarMain.run {
                title = "다중 이미지 업로드"
                inflateMenu(R.menu.main_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.mainMenuItem1 -> {
                            val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            albumIntent.setType("image/*")
                            val mimeType = arrayOf("image/*")
                            albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                            // 이미지 다중 선택 가증
                            albumIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            albumLauncher.launch(albumIntent)
                        }
                    }
                    false
                }
            }

            recyclerView.run {
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
            var imageViewRow: ImageView

            init {
                imageViewRow = rowBinding.imageViewRow
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolder = ViewHolder(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return viewHolder
        }

        override fun getItemCount(): Int {
            return imageList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Glide.with(this@MainActivity).load(imageList[position]).into(holder.imageViewRow)
        }
    }

    fun albumSetting() : ActivityResultLauncher<Intent> {

        val albumContract = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(albumContract) {

            if (it.resultCode == RESULT_OK) {
                val clipData = it.data?.clipData
                if (clipData != null) {
                    imageList.clear()
                    for (idx in 0 until clipData.itemCount) {
                        val imageUri = it.data!!.clipData!!.getItemAt(idx).uri
                        imageList.add(imageUri)
                    }
                } else {
                    val imageUri = it.data!!.data
                    imageList.add(imageUri!!)
                }
                activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        return albumLauncher
    }
}