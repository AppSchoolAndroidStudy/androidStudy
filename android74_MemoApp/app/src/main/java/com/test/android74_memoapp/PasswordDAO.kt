package com.test.android74_memoapp

import android.content.Context

class PasswordDAO {

    companion object{
        // select One
        fun selectOne(context: Context, passwordIdx:Int) : PasswordClass?{
            val sql = """
            select * from PasswordTable
            where password_idx = ?
        """.trimIndent()

            val args = arrayOf("$passwordIdx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)
            val chk = cursor.moveToNext()

            if(chk == true){
                val a1 = cursor.getColumnIndex("password_idx")
                val a2 = cursor.getColumnIndex("password_data")

                val passwordIdx = cursor.getInt(a1)
                val passwordData = cursor.getString(a2)

                val passwordClass = PasswordClass(passwordIdx, passwordData)
                return passwordClass
            } else {
                return null
            }
        }

        // select All
        fun selectAll(context: Context):MutableList<PasswordClass>{

            val passwordList = mutableListOf<PasswordClass>()

            val sql = """
            select * from PasswordTable
        """.trimIndent()

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            while(cursor.moveToNext()){
                val a1 = cursor.getColumnIndex("password_idx")
                val a2 = cursor.getColumnIndex("password_data")

                val passwordIdx = cursor.getInt(a1)
                val passwordData = cursor.getString(a2)

                val passwordClass = PasswordClass(passwordIdx, passwordData)

                passwordList.add(passwordClass)
            }

            dbHelper.close()

            return passwordList
        }

        // insert
        fun insert(context:Context, passwordClass: PasswordClass){

            val sql = """
            insert into PasswordTable
            (password_data) 
            values (?)
        """.trimIndent()

            val args = arrayOf(passwordClass.passwordData)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // update
        fun update(context:Context, passwordClass: PasswordClass){
            val sql = """
            update PasswordTable
            set password_data = ?
            where password_idx = ?
        """.trimIndent()

            val args = arrayOf(passwordClass.passwordData, passwordClass.passwordIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // delete
        fun delete(context:Context, passwordIdx:Int){

            val sql = """
            delete from PasswordTable
            where password_idx = ?
        """.trimIndent()

            val args = arrayOf(passwordIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}









