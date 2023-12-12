package com.test.retrofitex.repository

import com.test.retrofitex.data.model.APOD
import com.test.retrofitex.server.RetrofitClient
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
    ) : NasaRepository {
    override suspend fun getAPOD(): MutableList<APOD>? {
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