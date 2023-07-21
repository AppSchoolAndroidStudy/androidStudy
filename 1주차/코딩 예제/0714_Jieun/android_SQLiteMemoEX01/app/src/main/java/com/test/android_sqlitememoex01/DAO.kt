package com.test.android_sqlitememoex01

import android.content.Context

class DAO {
    companion object{

        fun insertData(context: Context, data:MemoClass){
            val sql = """insert into MemoTable
                |(title, content, date)
                |values (?,?,?)
            """.trimMargin()

            val args = arrayOf(
                data.title, data.content, data.date
            )

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun selectAllData(context: Context):MutableList<MemoClass>{
            val sql = "select * from MemoTable"
            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)
            val memoList = mutableListOf<MemoClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("title")
                val idx3 = cursor.getColumnIndex("content")
                val idx4 = cursor.getColumnIndex("date")

                val idx = cursor.getInt(idx1)
                val title = cursor.getString(idx2)
                val content = cursor.getString(idx3)
                val date = cursor.getString(idx4)

                val memo = MemoClass(idx, title, content, date)
                memoList.add(memo)
            }
            dbHelper.close()
            return memoList
        }

        fun selectData(context: Context, idx:Int):MemoClass{
            val sql = "select * from MemoTable where idx=?"
            val arg = arrayOf("$idx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg)
            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("title")
            val idx3 = cursor.getColumnIndex("content")
            val idx4 = cursor.getColumnIndex("date")

            val idx = cursor.getInt(idx1)
            val title = cursor.getString(idx2)
            val content = cursor.getString(idx3)
            val date = cursor.getString(idx4)

            val memo = MemoClass(idx, title, content, date)
            dbHelper.close()
            return memo
        }

        fun updateData(context: Context, memo:MemoClass){
            val sql = """update MemoTable
                |set title=?, content=?
                |where idx=?
            """.trimMargin()

            val args = arrayOf(memo.title, memo.content, memo.idx)
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun deleteData(context: Context, idx:Int){
            val sql = "delete from MemoTable where idx=?"
            val args = arrayOf(idx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}