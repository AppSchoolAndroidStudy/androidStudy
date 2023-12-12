package com.woojugoing.materialgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woojugoing.materialgallery.data.ImageItem
import com.woojugoing.materialgallery.databinding.RowBinding

class ImageAdapter(private val onItemClick: (ImageItem) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ViewHolderClass>() {

    private var imageList = listOf<ImageItem>()

    inner class ViewHolderClass(private val rowBinding: RowBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {

        fun bind(imageItem: ImageItem) {
            rowBinding.carouselImageView.setImageBitmap(imageItem.bitmap)
            rowBinding.root.setOnClickListener { onItemClick.invoke(imageItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val rowMainBinding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(rowMainBinding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.bind(imageList[position])
    }
}