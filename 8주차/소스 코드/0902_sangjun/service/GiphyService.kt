package likelion.project.giphy.service


import likelion.project.giphy.BuildConfig
import likelion.project.giphy.data.GiphyApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("v1/gifs/trending")
    suspend fun getGif(
        @Query("api_key") apiKey: String = BuildConfig.GIPHY_API_KEY
    ): Response<GiphyApiResponse>
}

