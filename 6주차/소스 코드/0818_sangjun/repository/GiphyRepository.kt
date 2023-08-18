package likelion.project.giphy.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.service.RetrofitClient

class GiphyRepository {
    companion object {
        suspend fun getGif(): Flow<Result<List<Giphy>>> {
            return flow {
                kotlin.runCatching {
                    RetrofitClient.getRetrofitService.getGif()
                }.onSuccess { response ->
                    emit(Result.success(response.body()!!.data))
                }.onFailure { exception ->
                    emit(Result.failure(exception))
                }
            }
        }
    }
}