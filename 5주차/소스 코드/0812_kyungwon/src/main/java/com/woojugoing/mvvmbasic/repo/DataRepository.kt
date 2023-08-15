package com.woojugoing.mvvmbasic.repo

import android.content.Context
import com.woojugoing.mvvmbasic.db.DBHelper
import com.woojugoing.mvvmbasic.model.DataClass1

// Database, Server처럼 Data를 가지고 있는 곳에서
// 데이터를 구해서 변환 하거나, 데이터의 저장, 삭제, 수정 등의 기능을 구현한다.
// 항상 호출해야 되니, 메서드를 바로 호출할 수 있게끔, Companion Object 처리를 해주면 편함.

class DataRepository {
    companion object{
        // Data 저장
        fun addData(context: Context, dataClass1: DataClass1){
            val dbHelper = DBHelper(context)
            val sql = "INSERT INTO Table1 (data1, data2) VALUES (?, ?)"
            val args = arrayOf(dataClass1.data1, dataClass1.data2)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // DataList 추출
        fun getDataList(context: Context): MutableList<DataClass1>{
            val dbHelper = DBHelper(context)
            val sql = "SELECT * FROM Table1 ORDER BY idx DESC"
            val dataList = mutableListOf<DataClass1>()

            val cursor1 = dbHelper.writableDatabase.rawQuery(sql, null)
            while(cursor1.moveToNext()) {
                val idx1 = cursor1.getColumnIndex("idx")
                val idx2 = cursor1.getColumnIndex("data1")
                val idx3 = cursor1.getColumnIndex("data2")

                val idx = cursor1.getInt(idx1)
                val data1 = cursor1.getString(idx2)
                val data2 = cursor1.getString(idx3)

                val list = DataClass1(idx, data1, data2)
                dataList.add(list)
            }
            dbHelper.close()
            return dataList
        }

        // Data 추출
        fun getData(context: Context, idx: Int): DataClass1 {
            val dbHelper = DBHelper(context)
            val sql = "SELECT * FROM Table1 WHERE idx = ?"
            val args = arrayOf(idx.toString())

            val cursor1 = dbHelper.writableDatabase.rawQuery(sql, args)
            cursor1.moveToNext()

            val idx2 = cursor1.getColumnIndex("data1")
            val idx3 = cursor1.getColumnIndex("data2")

            val data1 = cursor1.getString(idx2)
            val data2 = cursor1.getString(idx3)

            val list = DataClass1(idx, data1, data2)
            dbHelper.close()
            return list
        }
    }
}