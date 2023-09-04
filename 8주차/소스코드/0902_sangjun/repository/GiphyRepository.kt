package likelion.project.giphy.repository

import kotlinx.coroutines.flow.Flow
import likelion.project.giphy.data.Giphy

interface GiphyRepository {
    suspend fun getGif(): Flow<Result<List<Giphy>>>
}