package com.qure.calculator_tdd.data

import com.qure.calculator_tdd.domain.HistoryRepository
import com.qure.calculator_tdd.domain.model.Memory
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyDao: HistoryDao) :
    HistoryRepository {
    override suspend fun getAll(): List<Memory> {
        return historyDao.getAll()
            .map { it.convertHistoryToMemory() }
    }

    override suspend fun insert(memory: Memory) {
        historyDao.insert(History(memory.expression, memory.result))
    }

    override suspend fun removeAll() {
        historyDao.removeAll()
    }
}