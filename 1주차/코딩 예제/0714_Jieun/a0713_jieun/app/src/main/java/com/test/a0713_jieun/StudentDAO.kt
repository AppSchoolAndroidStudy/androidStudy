package com.test.a0713_jieun

import android.content.Context

class StudentDAO {
    companion object{

        fun insert(context: Context, studentData: StudentData){
            val sql = """
                insert into StudentTable
                (studentName, studentGrade, studentSchoolName)
                values (?, ?, ?)
            """.trimIndent()

            val args = arrayOf(studentData.studentName, studentData.studentGrade, studentData.studentSchoolName)

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        fun selectAll(context: Context, schoolName:String): MutableList<StudentData>{
            val sql = "select * from StudentTable where studentSchoolName=?"
            val studentList = mutableListOf<StudentData>()
            val args = arrayOf(schoolName)

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("studentIdx")
                val idx2 = cursor.getColumnIndex("studentName")
                val idx3 = cursor.getColumnIndex("studentGrade")
                val idx4 = cursor.getColumnIndex("studentSchoolName")

                val studentIdx = cursor.getInt(idx1)
                val studentName = cursor.getString(idx2)
                val studentGrade = cursor.getInt(idx3)
                val studentSchoolName = cursor.getString(idx4)

                val student = StudentData(studentIdx, studentName, studentGrade, studentSchoolName)

                studentList.add(student)
            }
            dbHelper.close()

            return studentList
        }

        fun selectOne(context: Context, studentIdx:Int): StudentData{
            val sql = """
                select * from StudentTable
                where studentIdx =?
            """.trimIndent()

            val args = arrayOf("$studentIdx")

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("studentIdx")
            val idx2 = cursor.getColumnIndex("studentName")
            val idx3 = cursor.getColumnIndex("studentGrade")
            val idx4 = cursor.getColumnIndex("studentSchoolName")

            val studentIdx = cursor.getInt(idx1)
            val studentName = cursor.getString(idx2)
            val studentGrade = cursor.getInt(idx3)
            val studentSchoolName = cursor.getString(idx4)

            val student = StudentData(studentIdx, studentName, studentGrade, studentSchoolName)

            return student
        }
    }
}