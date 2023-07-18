package com.test.android74_memoapp

import android.content.Context

class MemoDAO {

    companion object{
        // select One
        fun selectOne(context: Context, memoIdx:Int) : MemoClass?{
            val sql = """
            select * from MemoTable
            where memo_idx = ?
        """.trimIndent()

            val args = arrayOf("$memoIdx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)
            val chk = cursor.moveToNext()

            if(chk == true){
                val a1 = cursor.getColumnIndex("memo_idx")
                val a2 = cursor.getColumnIndex("memo_subject")
                val a3 = cursor.getColumnIndex("memo_text")
                val a4 = cursor.getColumnIndex("memo_date")
                val a5 = cursor.getColumnIndex("memo_category_idx")

                val memoIdx = cursor.getInt(a1)
                val memoSubject = cursor.getString(a2)
                val memoText = cursor.getString(a3)
                val memoDate = cursor.getString(a4)
                val memoCategoryIdx = cursor.getInt(a5)

                val memoClass = MemoClass(memoIdx, memoSubject, memoText, memoDate, memoCategoryIdx)
                return memoClass
            } else {
                return null
            }
        }

        // select All
        fun selectAll(context: Context):MutableList<MemoClass>{

            val memoList = mutableListOf<MemoClass>()

            val sql = """
                select * from MemoTable
                order by memo_idx desc
            """.trimIndent()

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            while(cursor.moveToNext()){

                val a1 = cursor.getColumnIndex("memo_idx")
                val a2 = cursor.getColumnIndex("memo_subject")
                val a3 = cursor.getColumnIndex("memo_text")
                val a4 = cursor.getColumnIndex("memo_date")
                val a5 = cursor.getColumnIndex("memo_category_idx")

                val memoIdx = cursor.getInt(a1)
                val memoSubject = cursor.getString(a2)
                val memoText = cursor.getString(a3)
                val memoDate = cursor.getString(a4)
                val memoCategoryIdx = cursor.getInt(a5)

                val memoClass = MemoClass(memoIdx, memoSubject, memoText, memoDate, memoCategoryIdx)

                memoList.add(memoClass)
            }

            dbHelper.close()

            return memoList
        }

        // select All - condition : category_idx
        fun selectAll(context: Context, categoryIdx:Int):MutableList<MemoClass>{

            val memoList = mutableListOf<MemoClass>()

            val sql = """
                select * from MemoTable
                where memo_category_idx = ?
                order by memo_idx desc
            """.trimIndent()

            val args = arrayOf("$categoryIdx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            while(cursor.moveToNext()){

                val a1 = cursor.getColumnIndex("memo_idx")
                val a2 = cursor.getColumnIndex("memo_subject")
                val a3 = cursor.getColumnIndex("memo_text")
                val a4 = cursor.getColumnIndex("memo_date")
                val a5 = cursor.getColumnIndex("memo_category_idx")

                val memoIdx = cursor.getInt(a1)
                val memoSubject = cursor.getString(a2)
                val memoText = cursor.getString(a3)
                val memoDate = cursor.getString(a4)
                val memoCategoryIdx = cursor.getInt(a5)

                val memoClass = MemoClass(memoIdx, memoSubject, memoText, memoDate, memoCategoryIdx)

                memoList.add(memoClass)
            }

            dbHelper.close()

            return memoList
        }

        // insert
        fun insert(context: Context, memoClass: MemoClass){

            val sql = """
            insert into MemoTable
            (memo_subject, memo_text, memo_date, memo_category_idx) 
            values (?, ?, ?, ?)
        """.trimIndent()

            val args = arrayOf(memoClass.memoSubject, memoClass.memoText, memoClass.memoDate, memoClass.memoCategoryIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // update
        fun update(context: Context, memoClass: MemoClass){
            val sql = """
            update MemoTable
            set memo_subject = ?, memo_text = ?, memo_date = ?, memo_category_idx = ?
            where  memo_idx = ?
        """.trimIndent()

            val args = arrayOf(memoClass.memoSubject, memoClass.memoText, memoClass.memoDate,
                                memoClass.memoCategoryIdx, memoClass.memoIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // delete
        fun delete(context: Context, memoIdx:Int){

            val sql = """
            delete from MemoTable
            where memo_idx = ?
        """.trimIndent()

            val args = arrayOf(memoIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // 특정 카테고리의 모든 메모를 삭제한다.
        fun deleteMemoInCategory(context: Context, categoryIdx:Int){
            val sql = """
                delete from MemoTable
                where memo_category_idx = ?
            """.trimIndent()

            val args = arrayOf(categoryIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}