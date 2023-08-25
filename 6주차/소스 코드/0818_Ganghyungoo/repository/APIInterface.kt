package repository

import model.APIObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("15111395/v1/uddi:8c528230-eda4-4d83-855a-bee73605e49f")
    fun getCampSiteList(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("returnType") returnType: String,
        @Query("serviceKey") serviceKey: String
    ): Call<APIObject>
}

object CampingService{
    val BASE_URL="https://api.odcloud.kr/api/"

    fun getCampingService() : APIInterface{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}