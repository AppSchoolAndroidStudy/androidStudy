package com.test.androidmini_memoapp2

import android.content.ContentValues
import android.content.Context

class CategoryDAO {
    companion object {
        fun insertCategory(context: Context, data: Category) {
            val sql="""insert into CategoryTable
                |(name)
                |values(?)
            """.trimMargin()
            val arg1= arrayOf(data.name)

            val sqLiteDatabase=DBHelper(context)
            sqLiteDatabase.writableDatabase.execSQL(sql,arg1)
            sqLiteDatabase.close()
        }

        fun selectData(context: Context, name: String): Category {
            //쿼리문
            val sql = "select * from CategoryTable where name=?"
            //?에 들어갈 값
            val arg1 = arrayOf("$name")
            //데이터베이스 오픈
            val dbHelper = DBHelper(context)
            //쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)
            cursor.moveToNext()//행하나를 가져오기 위해 반드시 해야함,

            //컬럼의 이름을 지정해서 순서값을 가져온다
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("name")

            //데이터를 가져온다
            val idx = cursor.getInt(idx1)
            val name = cursor.getString(idx2)

            val testClass = Category(idx, name)

            dbHelper.close()
            return testClass
        }

        fun selectAllData(context: Context): MutableList<Category> {
            //모든 행을 가져오는 쿼리문을 작성한다
            val sql = "select * from CategoryTable"
            //데이터베이스 오픈
            val dbHelper = DBHelper(context)
            //쿼리 실행
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            val dataList = mutableListOf<Category>()
            while (cursor.moveToNext()) {
                //컬럼의 이름을 지정해서 순서값을 가져온다
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("name")

                //데이터를 가져온다
                val idx = cursor.getInt(idx1)
                val name = cursor.getString(idx2)


                val testClass = Category(idx, name)
                dataList.add(testClass)
            }
            dbHelper.close()
            return dataList
        }

        fun updateData(context: Context, obj: Category) {
            //쿼리문
            val sql = """update CategoryTable
                |set name=?
                |where idx=?
            """.trimMargin()

            //?에 들어갈 값
            val args = arrayOf(obj.name, obj.idx)
            //쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun deleteData(context: Context, name: String) {
            //쿼리문
            val sql = "delete from CategoryTable where name = ?"
            //?에 들어갈 값
            val args = arrayOf(name)
            //쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

    }
}