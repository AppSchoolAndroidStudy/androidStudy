package com.test.retrofitex.repository

import com.test.retrofitex.data.model.APOD

interface NasaRepository {
    suspend fun getAPOD() : MutableList<APOD>?
}