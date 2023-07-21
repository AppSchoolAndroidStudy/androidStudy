package com.woojugoing.project_skw_0710

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Memo.db", null, 1) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        val sql = """
            create table MemoTable(
                idx integer primary key autoincrement,
                title text not null,
                content text not null,
                date text not null
            )
        """.trimIndent()
        sqliteDatabase?.execSQL(sql)
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("SQLite 자료의 내용 참고")
    }
}