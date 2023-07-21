package com.test.androidmini_memoapp2

import android.content.Context

class MemoDAO {
    companion object {

        fun insertData(context: Context, data: Memo) {
            val sql = """insert into MemoTable
                |(categoryId,memoName,memoDetail,date)
                |values(?,?,?,?)
            """.trimMargin()
            val arg1 = arrayOf(
                data.categoryId, data.memoName, data.memoDetail, data.date
            )

            val sqLiteDatabase = DBHelper(context)
            sqLiteDatabase.writableDatabase.execSQL(sql, arg1)
            sqLiteDatabase.close()
        }

        fun selectData(context: Context,Id:Int ,dataName: String): Memo {
            //쿼리문
            val sql = "select * from MemoTable where categoryId=? and memoName=?"
            //?에 들어갈 값
            val arg1 = arrayOf("$Id", dataName)
            //데이터베이스 오픈
            val dbHelper = DBHelper(context)
            //쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)
            cursor.moveToNext()//행하나를 가져오기 위해 반드시 해야함,

            //컬럼의 이름을 지정해서 순서값을 가져온다
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("categoryId")
            val idx3 = cursor.getColumnIndex("memoName")
            val idx4 = cursor.getColumnIndex("memoDetail")
            val idx5 = cursor.getColumnIndex("date")

            //데이터를 가져온다
            val idx = cursor.getInt(idx1)
            val categoryId = cursor.getInt(idx2)
            val name = cursor.getString(idx3)
            val detail = cursor.getString(idx4)
            val date = cursor.getString(idx5)

            val testClass = Memo(idx, categoryId, name, detail, date)

            dbHelper.close()
            return testClass
        }

        fun selectAllData(context: Context, id: Int): MutableList<Memo> {
            val sql = "SELECT * FROM MemoTable where categoryId=?"
            val arg1 = arrayOf(id.toString()) // id를 문자열로 변환하여 배열에 추가
            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)

            val dataList = mutableListOf<Memo>()
            cursor.use {
                while (cursor.moveToNext()) {
                    val idx1 = cursor.getColumnIndex("idx")
                    val idx2 = cursor.getColumnIndex("categoryId")
                    val idx3 = cursor.getColumnIndex("memoName")
                    val idx4 = cursor.getColumnIndex("memoDetail")
                    val idx5 = cursor.getColumnIndex("date")

                    val idx = cursor.getInt(idx1)
                    val categoryId = cursor.getInt(idx2)
                    val name = cursor.getString(idx3)
                    val detail = cursor.getString(idx4)
                    val date = cursor.getString(idx5)

                    val memo = Memo(idx, categoryId, name, detail, date)
                    dataList.add(memo)
                }
            }
            dbHelper.close()

            return dataList
        }

        fun updateData(context: Context, obj: Memo) {
            //쿼리문
            val sql = """update MemoTable
                |set memoName=?,memoDetail=?,date=?
                |where idx=?
            """.trimMargin()
            //?에 들어갈 값
            val args = arrayOf(obj.memoName, obj.memoDetail, obj.date, obj.idx)
            //쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun deleteData(context: Context, categoryId:Int, name: String) {
            //쿼리문
            val sql = "delete from MemoTable where categoryId=? and memoName = ? "
            //?에 들어갈 값
            val args = arrayOf("$categoryId",name)
            //쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }



    }
}