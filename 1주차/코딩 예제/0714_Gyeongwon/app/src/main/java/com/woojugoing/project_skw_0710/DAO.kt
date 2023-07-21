import com.woojugoing.project_skw_0710.DBHelper
import com.woojugoing.project_skw_0710.MemoClass
import android.content.Context

class DAO {

    companion object {

        fun insertData(context: Context, data: MemoClass) {
            val sql = """
                insert into MemoTable
                | (title, content, date)
                | values (?, ?, ?)
            """.trimMargin()

            val arg1 = arrayOf(data.title, data.content, data.date)
            val sqliteDatabase = DBHelper(context)
            sqliteDatabase.writableDatabase.execSQL(sql, arg1)
            sqliteDatabase.close()
        }

        fun selectData(context: Context, idx: Int): MemoClass {
            val sql = "select * from MemoTable where idx=?"
            val arg1 = arrayOf(idx.toString())

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, arg1)

            val memoClass: MemoClass

            if (cursor.moveToNext()) {
                val idxColumn = cursor.getColumnIndex("idx")
                val titleColumn = cursor.getColumnIndex("title")
                val contentColumn = cursor.getColumnIndex("content")
                val dateColumn = cursor.getColumnIndex("date")

                val idxValue = cursor.getInt(idxColumn)
                val titleValue = cursor.getString(titleColumn)
                val contentValue = cursor.getString(contentColumn)
                val dateValue = cursor.getString(dateColumn)

                memoClass = MemoClass(idxValue, titleValue, contentValue, dateValue)
            } else {
                memoClass = MemoClass(0, "", "", "0")
            }

            cursor.close()
            dbHelper.close()

            return memoClass
        }

        fun selectAllData(context: Context): MutableList<MemoClass> {
            val sql = "select * from MemoTable"

            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

            val dataList = mutableListOf<MemoClass>()

            while (cursor.moveToNext()) {
                val idxColumn = cursor.getColumnIndex("idx")
                val titleColumn = cursor.getColumnIndex("title")
                val contentColumn = cursor.getColumnIndex("content")
                val dateColumn = cursor.getColumnIndex("date")

                val idxValue = cursor.getInt(idxColumn)
                val titleValue = cursor.getString(titleColumn)
                val contentValue = cursor.getString(contentColumn)
                val dateValue = cursor.getString(dateColumn)

                val memoClass = MemoClass(idxValue, titleValue, contentValue, dateValue)
                dataList.add(memoClass)
            }

            cursor.close()
            dbHelper.close()

            return dataList
        }


        fun updateData(context:Context, obj:MemoClass){
            val sql = """
                | update MemoTable
                | set title=?, content=?, date=?
                | where idx=?
            """.trimMargin()

            val args = arrayOf(obj.title, obj.content, obj.date, obj.idx)
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }


        fun deleteData(context: Context, idx: Int){
            val sql = "delete from MemoTable where idx = ?"

            // ? 에 들어갈 값
            val args = arrayOf(idx)

            // Query 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }
}