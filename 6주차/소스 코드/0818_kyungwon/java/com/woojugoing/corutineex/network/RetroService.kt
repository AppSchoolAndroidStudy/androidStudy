package com.woojugoing.corutineex.network

import com.woojugoing.corutineex.model.RecyclerList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("repositories")
    suspend fun getDataFromApi(
        @Query("q") query: String
    ): RecyclerList
}