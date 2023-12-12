package com.androidstudy.databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangeNumViewModel : ViewModel() {
    val currentNum: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = 0
        }
    }

    fun decreaseNum() {
        val currentValue = currentNum.value ?: 0
        currentNum.value = currentValue - 1
    }

    fun increaseNum() {
        val currentValue = currentNum.value ?: 0
        currentNum.value = currentValue + 1
    }
}