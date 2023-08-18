package com.test.mvvmbasic.vm

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// UI 요소에 설정할 값을 관리한다.

class TestViewModel() : ViewModel() {
    // API 데이터를 가져오는 데 사용될 Repository
    private val apiRepository = TestDataRepository()

    var temp : Job? = null
    // LiveData를 사용하여 데이터를 관리
    private val _testDataList = MutableLiveData<List<TestDataClass>>()
    val testDataList: LiveData<List<TestDataClass>> get() = _testDataList

    // API에서 데이터 가져오는 함수
    fun fetchTestData() {

       viewModelScope.launch {
            val posts = apiRepository.getPosts()

            // UI 스레드에서 LiveData 업데이트

            _testDataList.value = posts

            Log.d("데이터", posts[0].toString())
            Log.d("데이터2", _testDataList.value?.get(0).toString())
            Log.d("데이터3", testDataList.value?.get(0).toString())

        }

    }

    override fun onCleared() {
        super.onCleared()
        temp?.cancel()
    }



}










