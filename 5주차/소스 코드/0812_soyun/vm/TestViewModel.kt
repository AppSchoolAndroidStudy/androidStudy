package com.test.mvvmbasic.vm

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository

// UI 요소에 설정할 값을 관리한다.

class TestViewModel() : ViewModel() {
    val testData1 = MutableLiveData<String>()
    val testData2 = MutableLiveData<String>()
    val testDataList = MutableLiveData<MutableList<TestDataClass>>()
    val testData = MutableLiveData<TestDataClass>()

    fun getDataList(context:Context){
        testDataList.value = TestDataRepository.getDataList(context)
    }

    fun setSelectedData(context: Context,testIdx:Int){
        testData.value = TestDataRepository.getData(context, testIdx)
    }

}










