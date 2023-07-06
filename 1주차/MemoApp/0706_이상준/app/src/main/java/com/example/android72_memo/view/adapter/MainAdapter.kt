package com.example.android72_memo.view.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android72_memo.databinding.MainRowBinding
import com.example.android72_memo.model.Memo

class MainAdapter(
    val memoList: List<Memo>,
    val fragment: Fragment,
    val onMemoClickListener: (Memo) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainVieHolder>() {
    inner class MainVieHolder(mainRowBinding: MainRowBinding) :
        RecyclerView.ViewHolder(mainRowBinding.root) {
        var textViewRowDate: TextView
        var textViewRowTitle: TextView

        init {
            textViewRowDate = mainRowBinding.textViewRowDate
            textViewRowTitle = mainRowBinding.textViewRowTitle
            mainRowBinding.root.run {
                setOnClickListener {
                    onMemoClickListener(memoList[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVieHolder {
        val rowBinding = MainRowBinding.inflate(fragment.layoutInflater)
        val viewHolder = MainVieHolder(rowBinding)
        val layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        rowBinding.root.layoutParams = layoutParams
        return viewHolder
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    override fun onBindViewHolder(holder: MainVieHolder, position: Int) {
        holder.textViewRowDate.text = memoList[position].date
        holder.textViewRowTitle.text = memoList[position].title
    }
}