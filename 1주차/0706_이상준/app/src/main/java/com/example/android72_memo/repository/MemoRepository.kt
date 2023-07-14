package com.example.android72_memo.repository

import android.content.Context
import com.example.android72_memo.db.DAO
import com.example.android72_memo.model.Memo

class MemoRepository {
    private val dao = DAO()

    fun insertMemo(context: Context, memo: Memo) {
        dao.insert(context, memo)
    }

    fun getMemo(context: Context, uuid: String): Memo {
        return dao.getMemo(context, uuid)
    }

    fun getAllMemo(context: Context): List<Memo> {
        return dao.getAllMemo(context)
    }

    fun updateMemo(context: Context, memo: Memo): Memo {
        return dao.updateMemo(context, memo)
    }

    fun deleteMemo(context: Context, uuid: String) {
        dao.deleteMemo(context, uuid)
    }
}