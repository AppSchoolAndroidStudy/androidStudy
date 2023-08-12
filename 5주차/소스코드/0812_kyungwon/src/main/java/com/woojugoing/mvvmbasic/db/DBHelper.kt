package com.woojugoing.mvvmbasic.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "MVVM.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """CREATE TABLE Table1(
            |  idx integer PRIMARY KEY AUTOINCREMENT,
            |  data1 text NOT NULL,
            |  data2 text NOT NULL)
        """.trimMargin()
        db?.execSQL(sql)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}