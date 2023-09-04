package com.test.retrofitex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import com.test.retrofitex.repository.NasaRepository
import com.test.retrofitex.server.RetrofitClient
import com.test.retrofitex.ui.ResultActivity
import com.test.retrofitex.viewmodel.NasaViewModel
import com.test.retrofitex.viewmodel.NasaViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }

    private lateinit var retrofitClient : RetrofitClient


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        retrofitClient = RetrofitClient()
    }

    @Test
    fun apod_isNotNull(){
        //assertNotNull - 입력이 null이 아니면 성공, null이면 실패
        assertNotNull(NasaRepository(retrofitClient))
    }


}