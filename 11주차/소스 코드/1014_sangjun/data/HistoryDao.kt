package com.qure.calculator_tdd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History")
    suspend fun getAll(): List<History>

    @Insert
    suspend fun insert(vararg history: History)

    @Query("DELETE FROM History")
    suspend fun removeAll()
}