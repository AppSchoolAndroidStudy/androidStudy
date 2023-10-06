package likelion.project.giphy.ui

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.databinding.ItemGifBinding

class MainAdapter(private val onClickItemGif: (giphy: Giphy) -> Unit): ListAdapter<Giphy, MainAdapter.GifViewHolder>(DIFFUTIL) {

    inner class GifViewHolder(private val binding: ItemGifBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(giphy: Giphy) {
            Glide.with(itemView)
                .asGif()
                .load(giphy.images.original.url)
                .into(binding.ivGif)
            binding.tvTitle.text = giphy.title
            binding.root.setOnClickListener {
                onClickItemGif.invoke(giphy)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFFUTIL = object : DiffUtil.ItemCallback<Giphy>() {
            override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem == newItem
            }

        }
    }
}