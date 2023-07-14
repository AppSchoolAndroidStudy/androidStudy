package com.example.android72_memo.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

data class Memo(
    val title: String = "",
    val content: String = "",
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val uuid: String = UUID.randomUUID().toString()
)
