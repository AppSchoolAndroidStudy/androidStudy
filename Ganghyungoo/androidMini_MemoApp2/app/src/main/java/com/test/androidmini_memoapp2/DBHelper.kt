package com.test.androidmini_memoapp2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Test.db", null, 1) {
    //테이블을 생성
    override fun onCreate(db: SQLiteDatabase?) {

        val categoryTableSql = """create table CategoryTable
            (idx integer primary key autoincrement,
            name text not null
            )
        """.trimIndent()
        db?.execSQL(categoryTableSql)


        //메모테이블의 categoryId는 카테고리의 idx를 참조하며 on delete cascade를 통해 참조되던 카테고리가 삭제되면 참조하고있는 메모도 삭제될 것.
        val memoTableSql = """create table MemoTable 
            (idx integer primary key autoincrement,
            categoryId integer,
            memoName text not null,
            memoDetail text not null,
            date text not null,
            foreign key (categoryId) references CategoryTable (idx) on delete cascade
            )
        """.trimIndent()
        db?.execSQL(memoTableSql)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}