package com.qure.calculator_tdd.domain

import com.qure.calculator_tdd.domain.model.Memory

interface HistoryRepository {
    suspend fun getAll(): List<Memory>
    suspend fun insert(memory: Memory)
    suspend fun removeAll()
}
