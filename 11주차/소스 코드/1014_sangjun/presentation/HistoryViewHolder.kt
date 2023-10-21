package com.qure.calculator_tdd.presentation

import androidx.recyclerview.widget.RecyclerView
import com.qure.calculator_tdd.data.History
import com.qure.calculator_tdd.databinding.ItemResultBinding
import com.qure.calculator_tdd.domain.model.Memory

class HistoryViewHolder(private val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(memory: Memory) {
        binding.model = memory
        binding.executePendingBindings()
    }
}