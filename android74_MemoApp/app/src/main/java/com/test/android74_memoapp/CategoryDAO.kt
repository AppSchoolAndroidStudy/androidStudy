package com.test.android74_memoapp

import android.content.Context

class CategoryDAO {


    companion object{
        // select One
        fun selectOne(context: Context, categoryIdx:Int) : CategoryClass?{
            val sql = """
            select * from CategoryTable
            where category_idx = ?
        """.trimIndent()

            val args = arrayOf("$categoryIdx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)
            val chk = cursor.moveToNext()

            if(chk == true){
                val a1 = cursor.getColumnIndex("category_idx")
                val a2 = cursor.getColumnIndex("category_name")

                val categoryIdx = cursor.getInt(a1)
                val categoryName = cursor.getString(a2)

                val categoryClass = CategoryClass(categoryIdx, categoryName)
                return categoryClass
            } else {
                return null
            }
        }

        // select All
        fun selectAll(context: Context):MutableList<CategoryClass>{

            val categoryList = mutableListOf<CategoryClass>()

            val sql = """
                select * from CategoryTable
                order by category_idx desc
            """.trimIndent()

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            while(cursor.moveToNext()){
                val a1 = cursor.getColumnIndex("category_idx")
                val a2 = cursor.getColumnIndex("category_name")

                val categoryIdx = cursor.getInt(a1)
                val categoryName = cursor.getString(a2)

                val categoryClass = CategoryClass(categoryIdx, categoryName)

                categoryList.add(categoryClass)
            }

            dbHelper.close()

            return categoryList
        }

        // insert
        fun insert(context: Context, categoryClass: CategoryClass){

            val sql = """
            insert into CategoryTable
            (category_name) 
            values (?)
        """.trimIndent()

            val args = arrayOf(categoryClass.categoryName)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // update
        fun update(context: Context, categoryClass: CategoryClass){
            val sql = """
            update CategoryTable
            set category_name = ?
            where  category_idx = ?
        """.trimIndent()

            val args = arrayOf(categoryClass.categoryName, categoryClass.categoryIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // delete
        fun delete(context: Context, categoryIdx:Int){

            val sql = """
            delete from CategoryTable
            where category_idx = ?
        """.trimIndent()

            val args = arrayOf(categoryIdx)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}