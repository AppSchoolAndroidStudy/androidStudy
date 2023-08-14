package com.test.mvvmbasic.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository

//viewmodel : UI 요소에 설정할 값을 관리
//화면과 관련된 요소들만 넣어야함
class TestViewModel() : ViewModel() {
    val testData1 = MutableLiveData<String>()
    val testData2 = MutableLiveData<String>()
    val testDataList = MutableLiveData<MutableList<TestDataClass>>()
    val testDataResult1 = MutableLiveData<String>()
    val testDataResult2 = MutableLiveData<String>()

    fun getDataList(context: Context){
        testDataList.value = TestDataRepository.getDataList(context)
    }

    fun getOneData(context: Context, position:Int){
        testDataResult1.value = TestDataRepository.getOneData(context, position).testData1
        testDataResult2.value = TestDataRepository.getOneData(context, position).testData2
    }
}