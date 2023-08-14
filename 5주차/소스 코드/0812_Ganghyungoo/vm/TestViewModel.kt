package com.test.mvvmbasic.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository

// UI 요소에 설정할 값을 관리한다.

class TestViewModel() : ViewModel() {
    val testData1 = MutableLiveData<String>()
    val testData2 = MutableLiveData<String>()
    val testDataList = MutableLiveData<MutableList<TestDataClass>>()

    fun getDataList(context:Context){
        testDataList.value = TestDataRepository.getDataList(context)
    }
}










