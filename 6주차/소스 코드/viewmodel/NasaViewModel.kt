package com.test.retrofitex.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.retrofitex.data.model.APOD
import com.test.retrofitex.repository.NasaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NasaViewModel(private val nasaRepository: NasaRepository) : ViewModel(){
    val scope = CoroutineScope(Dispatchers.Main)

    val apodList = MutableLiveData<MutableList<APOD>>()

    val apodDate = MutableLiveData<String>()
    val apodExplanation = MutableLiveData<String>()
    val apodHdurl = MutableLiveData<String>()
    val apodTitle = MutableLiveData<String>()

    fun getAPOD(){
        scope.launch {
            apodList.value = nasaRepository.getAPOD()
        }
    }
}