package com.example.android72_memo.viewmodel

import android.content.Context
import com.example.android72_memo.model.Memo
import com.example.android72_memo.repository.MemoRepository

class MemoViewModel(val context: Context) {
    private val memoRepository = MemoRepository()

    private var _memos: List<Memo> = emptyList()
    val memos get() = _memos

    private var _memo: Memo = Memo()
    val memo get() = _memo

    fun insertMemo(memo: Memo) {
        memoRepository.insertMemo(context, memo)
    }

    fun getAllMemo() {
        _memos = memoRepository.getAllMemo(context)
    }

    fun getMemo(uuid: String) {
        try {
            _memo = memoRepository.getMemo(context, uuid)
        } catch (e: Exception) {
            _memo = Memo()
        }
    }

    fun updateMemo(memo: Memo) {
        _memo = memoRepository.updateMemo(context, memo)
    }

    fun deleteMemo(uuid: String) {
        memoRepository.deleteMemo(context, uuid)
    }
}