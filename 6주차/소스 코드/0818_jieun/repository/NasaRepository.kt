package com.test.retrofitex.repository

import android.util.Log
import com.test.retrofitex.data.model.APOD
import com.test.retrofitex.server.RetrofitClient

class NasaRepository (private val retrofitClient: RetrofitClient) {
    suspend fun getAPOD(): MutableList<APOD>?{
        try {
            val response = retrofitClient.api.getAPOD()
            if (response.isSuccessful) {
                return response.body()
            } else {
                return null
            }
        } catch (e:Exception) {
            e.stackTrace
            return null
        }
    }
}