package com.test.android74_memoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context) : SQLiteOpenHelper(context, "Memo.db", null, 1) {
    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        // PasswordTable
        val sql1 = """
            create table PasswordTable(
                password_idx integer primary key autoincrement,
                password_data text not null
            )
        """.trimIndent()

        sqliteDatabase?.execSQL(sql1)

        // CategoryTable
        val sql2 = """
            create table CategoryTable(
                category_idx integer primary key autoincrement,
                category_name text not null
            )
        """.trimIndent()
        sqliteDatabase?.execSQL(sql2)

        // MemoTable
        val sql3 = """
            create table MemoTable(
                memo_idx integer primary key autoincrement,
                memo_subject text not null,
                memo_text text not null,
                memo_date date not null,
                memo_category_idx integer not null
            )
        """.trimIndent()
        sqliteDatabase?.execSQL(sql3)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}