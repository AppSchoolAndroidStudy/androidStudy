package com.woojugoing.mvvmbasic.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woojugoing.mvvmbasic.MainActivity
import com.woojugoing.mvvmbasic.model.DataClass1
import com.woojugoing.mvvmbasic.repo.DataRepository

// UI 요소에 설정할 값을 관리

class ViewModel1: ViewModel() {
    val data1 = MutableLiveData<String>()
    val data2 = MutableLiveData<String>()
    val dataList = MutableLiveData<MutableList<DataClass1>>()

    fun getDataList(context: Context){
        dataList.value = DataRepository.getDataList(context)
    }
}