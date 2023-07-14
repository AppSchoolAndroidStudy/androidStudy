package com.example.android72_memo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "memo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """create table MemoTable
            (title text not null,
            content text not null,
            date text not null,
            uuid text not null)
        """.trimIndent()

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}