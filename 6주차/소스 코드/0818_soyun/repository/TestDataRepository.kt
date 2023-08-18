package com.test.mvvmbasic.repository

import android.content.Context
import com.test.mvvmbasic.model.TestDataClass
import com.test.study06_coroutine.service.TestDataApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 데이터베이스나 서버 등 데이터를 가지고 있는 곳에서
// 데이터를 구해 반환하거나 데이터를 저장, 수정, 삭제 등을 구현한다.

class TestDataRepository {

    private val testDataApiService: TestDataApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://koreanjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        testDataApiService = retrofit.create(TestDataApiService::class.java)
    }

    suspend fun getPosts(): List<TestDataClass> {
        val response = testDataApiService.getPosts()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}




