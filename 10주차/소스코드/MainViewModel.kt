package com.test.databindingproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    companion object {
        var selectedUnit = ""
        fun settingSelectedUnit(unit: String) {
            selectedUnit = unit
        }
    }

    var changeValue = MutableLiveData<String>("0")
    var mmValue = MutableLiveData<String>()
    var cmValue = MutableLiveData<String>()
    var mValue = MutableLiveData<String>()
    var kmValue = MutableLiveData<String>()
    var inchValue = MutableLiveData<String>()


    fun updateConversion(changeValue:String) {
        var value=changeValue
        if (changeValue.isEmpty()){
            value="0"
        }
        try {
            when (selectedUnit) {
                "CM" -> {
                    mmValue.value=(value.toDouble() * 10.0).toString()
                    cmValue.value=(value.toDouble()).toString()
                    mValue.value=(value.toDouble() / 100.0).toString()
                    kmValue.value=(value.toDouble() / 100000.0).toString()
                    inchValue.value=(value.toDouble() / 2.54).toString()

                }

                "M" -> {
                    mmValue.value=(value.toDouble() * 1000.0).toString()
                    cmValue.value=(value.toDouble() * 100.0).toString()
                    mValue.value=(value.toDouble()).toString()
                    kmValue.value=(value.toDouble()*0.001).toString()
                    inchValue.value=(value.toDouble()*39.3701).toString()
                }

                "KM" -> {
                    mmValue.value=(value.toDouble() * 1000000.0).toString()
                    cmValue.value=(value.toDouble() * 100000.0).toString()
                    mValue.value=(value.toDouble() * 1000.0).toString()
                    kmValue.value=(value.toDouble()).toString()
                    inchValue.value=(value.toDouble() * 39370.1).toString()
                }

            }

        }catch (e:NumberFormatException){

        }


    }


}