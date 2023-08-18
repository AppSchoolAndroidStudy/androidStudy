package com.test.mvvmbasic.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.test.mvvmbasic.db.DBHelper
import com.test.mvvmbasic.model.TestDataClass

// 데이터베이스나 서버 등 데이터를 가지고 있는 곳에서
// 데이터를 구해 반환하거나 데이터를 저장, 수정, 삭제 등을 구현한다.

class TestDataRepository {

    companion object{
        // 데이터를 저장한다.
        fun addData(context:Context, testDataClass:TestDataClass){
            val dbHelper = DBHelper(context)

            val sql = """insert into TestTable
            | (testData1, testData2)
            | values (?, ?)
        """.trimMargin()

            val args = arrayOf(testDataClass.testData1, testDataClass.testData2)

            dbHelper.writableDatabase.execSQL(sql, args)

            dbHelper.close()
        }

        // 모든 데이터를 가져온다.
        fun getDataList(context:Context) : MutableList<TestDataClass>{
            val dbHelper = DBHelper(context)
            val sql = "select * from TestTable order by testIdx desc"

            val dataList = mutableListOf<TestDataClass>()

            val c1 = dbHelper.writableDatabase.rawQuery(sql, null)
            while(c1.moveToNext()){
                val idx1 = c1.getColumnIndex("testIdx")
                val idx2 = c1.getColumnIndex("testData1")
                val idx3 = c1.getColumnIndex("testData2")

                val testIdx = c1.getInt(idx1)
                val testData1 = c1.getString(idx2)
                val testData2 = c1.getString(idx3)

                val t1 = TestDataClass(testIdx, testData1, testData2)
                dataList.add(t1)
            }

            dbHelper.close()
            return dataList
        }

        //조건에 맞는 데이터 하나를 가져온다
        fun getOneData(context: Context,idx:Int):TestDataClass?{
            val sql="""
                select * from TestTable
                where testIdx=?
            """.trimIndent()
            val dbHelper = DBHelper(context)
            val args= arrayOf("$idx")
            val cursor=dbHelper.writableDatabase.rawQuery(sql,args)
            val chk=cursor.moveToNext()

            if(chk == true){
                val a1 = cursor.getColumnIndex("testIdx")
                val a2 = cursor.getColumnIndex("testData1")
                val a3 = cursor.getColumnIndex("testData2")

                val testIdx = cursor.getInt(a1)
                val testData1 = cursor.getString(a2)
                val testData2 = cursor.getString(a3)

                val testDataClass = TestDataClass(testIdx,testData1,testData2)
                return testDataClass
            } else {
                return null
            }

        }

    }
}




