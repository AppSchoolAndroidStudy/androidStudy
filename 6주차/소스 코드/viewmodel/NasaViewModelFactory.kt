package com.test.retrofitex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.retrofitex.repository.NasaRepository
import java.lang.IllegalArgumentException

//ViewModelProvider.Factory 구현

class NasaViewModelFactory(private val nasaRepository: NasaRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //modelClass에 TestViewModel이 상속되었는지 물어봄
        if(modelClass.isAssignableFrom(NasaViewModel::class.java)){
            return NasaViewModel(nasaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}