package com.test.study06_coroutine.service

import com.test.mvvmbasic.model.TestDataClass
import retrofit2.Response
import retrofit2.http.GET


interface TestDataApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<TestDataClass>>

}
