package com.test.retrofitex.server

import com.test.retrofitex.data.api.NasaAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : NasaAPI = retrofit.create(NasaAPI::class.java)
}