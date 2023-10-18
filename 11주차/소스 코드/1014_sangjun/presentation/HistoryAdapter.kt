package com.qure.calculator_tdd.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.qure.calculator_tdd.databinding.ItemResultBinding
import com.qure.calculator_tdd.domain.model.Memory

class HistoryAdapter : ListAdapter<Memory, HistoryViewHolder>(HistoryDiffiUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}