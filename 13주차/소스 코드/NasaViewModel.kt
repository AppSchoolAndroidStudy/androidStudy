package com.test.retrofitex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.retrofitex.data.model.APOD
import com.test.retrofitex.repository.NasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaViewModel @Inject constructor(private val nasaRepository: NasaRepository) : ViewModel(){

    private val _apodList = MutableLiveData<MutableList<APOD>>()
    val apodList : LiveData<MutableList<APOD>>
        get() = _apodList

    val apodDate = MutableLiveData<String>()
    val apodExplanation = MutableLiveData<String>()
    val apodHdurl = MutableLiveData<String>()
    val apodTitle = MutableLiveData<String>()

    fun getAPOD(){
        viewModelScope.launch {
            _apodList.value = nasaRepository.getAPOD()
        }
    }
}