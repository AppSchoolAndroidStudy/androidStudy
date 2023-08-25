package com.test.retrofitex.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.test.retrofitex.databinding.ItemBinding
import com.test.retrofitex.viewmodel.NasaViewModel

class Adapter(private val nasaViewModel : NasaViewModel, val context : Context, val listener:(Int)->Unit) : RecyclerView.Adapter<Adapter.ViewHolderClass>(){
    inner class ViewHolderClass(itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var imageViewItem : ImageView
        var textViewItemTitle : TextView
        var textViewItemDate : TextView
        var progressBarItem : ProgressBar

        init {
            imageViewItem = itemBinding.imageViewItem
            textViewItemTitle = itemBinding.textViewItemTitle
            textViewItemDate = itemBinding.textViewItemDate
            progressBarItem = itemBinding.progressBarItem

            itemBinding.root.setOnClickListener{listener(adapterPosition)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context))

        itemBinding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return ViewHolderClass(itemBinding)
    }

    override fun getItemCount(): Int {
        return nasaViewModel.apodList.value?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.progressBarItem.visibility = View.VISIBLE
        Glide.with(context).load(nasaViewModel.apodList.value?.get(position)?.hdurl)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBarItem.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBarItem.visibility = View.GONE
                    return false
                }
            })
            .into(holder.imageViewItem)

        holder.textViewItemTitle.text = nasaViewModel.apodList.value?.get(position)?.title
        holder.textViewItemDate.text = nasaViewModel.apodList.value?.get(position)?.date
    }
}


