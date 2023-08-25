package com.example.coroutine.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutine.MainActivity
import com.example.coroutine.model.DataClass
import com.example.coroutine.repository.DataRepository


class MainViewModel : ViewModel() {

    val dataList = MutableLiveData<MutableList<DataClass>>()

    fun getDataList(context: Context) {
        dataList.value = DataRepository.getDataList(context)
    }
}