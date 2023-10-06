package likelion.project.giphy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.data.Images
import likelion.project.giphy.data.Original
import likelion.project.giphy.repository.GiphyRepository
import likelion.project.giphy.service.RetrofitClient

class FakeRepository : GiphyRepository {

    var giphy: List<Giphy>? = null

    override suspend fun getGif(): Flow<Result<List<Giphy>>> {
        return flow {
            if (giphy != null) {
                emit(Result.success(giphy!!))
            } else {
                emit(Result.failure(Exception("테스트 실패")))
            }
        }
    }
}