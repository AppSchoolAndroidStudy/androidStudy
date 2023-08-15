package com.test.mvvmbasic.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository

class ShowItemModel: ViewModel() {
    val showIdx = MutableLiveData<Int>()
    val showObject= MutableLiveData<TestDataClass>()

    fun getShowObject(context: Context,idx:Int){
        showObject.value= TestDataRepository.getOneData(context,idx)
    }
}