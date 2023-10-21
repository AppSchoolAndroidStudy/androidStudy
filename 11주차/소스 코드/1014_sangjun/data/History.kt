package com.qure.calculator_tdd.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qure.calculator_tdd.domain.model.Memory

@Entity
data class History(
    val expression: String,
    val result: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    fun convertHistoryToMemory(): Memory {
        return Memory(
            this.expression,
            this.result
        )
    }
}