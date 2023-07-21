package com.test.a0713_jieun

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context) :SQLiteOpenHelper(context, "School.db", null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        //School table
        val sql1 = """
            create table SchoolTable(
                schoolIdx integer primary key autoincrement,
                schoolName text not null
            )
        """.trimIndent()

        p0?.execSQL(sql1)

        //Student Table
        val sql2 = """
            create table StudentTable(
                studentIdx integer primary key autoincrement,
                studentName text not null,
                studentGrade integer not null,
                studentSchoolName text not null,
                foreign key (studentSchoolName) references SchoolTable(schoolName)
            )
        """.trimIndent()

        p0?.execSQL(sql2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}