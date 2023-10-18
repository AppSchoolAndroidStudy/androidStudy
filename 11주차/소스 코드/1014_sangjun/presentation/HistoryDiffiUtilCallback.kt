package com.qure.calculator_tdd.presentation

import androidx.recyclerview.widget.DiffUtil
import com.qure.calculator_tdd.data.History
import com.qure.calculator_tdd.domain.model.Memory

class HistoryDiffiUtilCallback : DiffUtil.ItemCallback<Memory>() {
    override fun areItemsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem.expression == newItem.expression && oldItem.result == newItem.result
    }

    override fun areContentsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem == newItem
    }
}
