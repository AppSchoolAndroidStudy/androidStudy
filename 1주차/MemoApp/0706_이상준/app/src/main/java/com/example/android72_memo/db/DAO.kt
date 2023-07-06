package com.example.android72_memo.db

import android.content.ContentValues
import android.content.Context
import com.example.android72_memo.model.Memo

class DAO {

    fun insert(context: Context, memo: Memo) {
        val contentValues = ContentValues()

        contentValues.run {
            put("title", memo.title)
            put("content", memo.content)
            put("date", memo.date)
            put("uuid", memo.uuid)
        }
        val dbHelper = DBHelper(context)
        dbHelper.run {
            writableDatabase.insert("MemoTable", null, contentValues)
            close()
        }
    }

    fun getMemo(context: Context, uuid: String): Memo {

        val dbHelper = DBHelper(context)

        val selection = "uuid = ?"
        val args = arrayOf(uuid)
        val cursor =
            dbHelper.writableDatabase.query("MemoTable", null, selection, args, null, null, null)
        cursor.moveToNext()

        val idx1 = cursor.getColumnIndex("title")
        val idx2 = cursor.getColumnIndex("content")
        val idx3 = cursor.getColumnIndex("date")
        val idx4 = cursor.getColumnIndex("uuid")

        val title = cursor.getString(idx1)
        val content = cursor.getString(idx2)
        val date = cursor.getString(idx3)
        val uuid = cursor.getString(idx4)

        val memo = Memo(title, content, date,uuid)
        dbHelper.close()
        return memo
    }


    fun getAllMemo(context: Context): List<Memo> {

        val dbHelper = DBHelper(context)
        val cursor =
            dbHelper.writableDatabase.query("MemoTable", null, null, null, null, null, null)
        val memoList = mutableListOf<Memo>()

        while (cursor.moveToNext()) {
            val idx1 = cursor.getColumnIndex("title")
            val idx2 = cursor.getColumnIndex("content")
            val idx3 = cursor.getColumnIndex("date")
            val idx4 = cursor.getColumnIndex("uuid")

            val title = cursor.getString(idx1)
            val content = cursor.getString(idx2)
            val date = cursor.getString(idx3)
            val uuid = cursor.getString(idx4)

            val memo = Memo(title, content, date, uuid)
            memoList.add(memo)
        }
        dbHelper.close()

        return memoList.reversed()
    }

    fun updateMemo(context: Context, memo: Memo): Memo {
        val cv = ContentValues()
        cv.run {
            put("title", memo.title)
            put("content", memo.content)
            put("date", memo.date)
            put("uuid", memo.uuid)
        }

        val condition = "uuid = ?"

        val args = arrayOf("${memo.uuid}")

        val dbHelper = DBHelper(context)

        dbHelper.writableDatabase.update("MemoTable", cv, condition, args)
        dbHelper.close()

        return memo
    }

    fun deleteMemo(context: Context, uuid: String) {
        val condition = "uuid = ?"
        val args = arrayOf(uuid)

        val dbHelper = DBHelper(context)
        dbHelper.writableDatabase.delete("MemoTable", condition, args)
        dbHelper.close()
    }
}
