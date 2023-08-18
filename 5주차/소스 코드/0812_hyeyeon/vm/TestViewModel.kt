package com.test.mvvmbasic.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository


// UI 요소에 설정할 값을 관리한다.

class TestViewModel : ViewModel() {
    val testData1 = MutableLiveData<String>()
    val testData2 = MutableLiveData<String>()
    val testDataList = MutableLiveData<MutableList<TestDataClass>>()

    fun getDataList(context: Context) {
        testDataList.value = TestDataRepository.getDataList(context)
    }

    fun getDataOne(context: Context, testIdx: Int) {
        val t1 = TestDataRepository.getDataOne(context, testIdx)

        testData1.value = t1.testData1
        testData2.value = t1.testData2
    }
}










