package com.androidstudy.hilt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    private val _description = MutableLiveData<String>()

    val title: LiveData<String>
        get() = _title
    val description: LiveData<String>
        get() = _description

    init {
        _title.value = "제목 불러오기 전"
        _description.value = "내용 불러오기 전"
    }

    fun fetchMemo() {
        memoRepository.getMemo {
            _title.value = it.result.get("title").toString()
            _description.value = it.result.get("description").toString()
        }
    }

}