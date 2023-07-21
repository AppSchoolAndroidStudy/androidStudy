package com.test.a0713_jieun

import android.content.Context

class SchoolDAO {
    companion object{

        fun insert(context: Context, schoolData: SchoolData){
            val sql = """
                insert into SchoolTable
                (schoolName)
                values (?)
            """.trimIndent()

            val args = arrayOf(schoolData.schoolName)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun selectAll(context: Context): MutableList<SchoolData>{
            val sql = "select * from SchoolTable"
            val schoolList = mutableListOf<SchoolData>()

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("schoolIdx")
                val idx2 = cursor.getColumnIndex("schoolName")

                val schoolIdx = cursor.getInt(idx1)
                val schoolName = cursor.getString(idx2)

                val school = SchoolData(schoolIdx, schoolName)

                schoolList.add(school)
            }
            dbHelper.close()

            return schoolList
        }

        fun delete(context:Context){
            val sql = "delete from SchoolTable"

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, null)
            dbHelper.close()
        }
    }
}