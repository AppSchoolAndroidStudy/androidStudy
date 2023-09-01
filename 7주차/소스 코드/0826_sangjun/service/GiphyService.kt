package likelion.project.compose_recyclerview.service


import likelion.project.compose_recyclerview.BuildConfig
import likelion.project.compose_recyclerview.model.GiphyApiResponse


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("v1/gifs/trending")
    suspend fun getGif(
        @Query("api_key") apiKey: String = BuildConfig.GIPHY_API_KEY
    ): Response<GiphyApiResponse>
}

