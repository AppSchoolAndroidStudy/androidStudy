package likelion.project.compose_recyclerview.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import likelion.project.compose_recyclerview.model.Giphy
import likelion.project.compose_recyclerview.service.RetrofitClient


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