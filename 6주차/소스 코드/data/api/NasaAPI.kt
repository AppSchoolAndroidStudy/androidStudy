package com.test.retrofitex.data.api

import com.test.retrofitex.data.model.APOD
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {
    @GET("planetary/apod")
    suspend fun getAPOD(
        @Query("api_key") apiKey:String = "04bYhMaSVulce2fPwovrqEPXwe9zttnFhtQgxD0Z",
        @Query("start_date") startDate:String = "2023-07-01"
    ):Response<MutableList<APOD>>
}