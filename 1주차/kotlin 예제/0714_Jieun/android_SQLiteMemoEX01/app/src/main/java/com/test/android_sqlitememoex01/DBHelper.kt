package com.test.android_sqlitememoex01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "Memo.db", null, 1) {
    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        //메모 테이블 쿼리
        val sql ="""create table MemoTable
            (idx integer primary key autoincrement,
            title text not null,
            content text not null,
            date date not null)
        """.trimIndent()

        //테이블 생성
        sqliteDatabase?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}