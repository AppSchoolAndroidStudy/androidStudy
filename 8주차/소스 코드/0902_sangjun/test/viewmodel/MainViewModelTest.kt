package likelion.project.giphy.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.data.Images
import likelion.project.giphy.data.Original
import likelion.project.giphy.repository.GiphyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel
    private val repository: GiphyRepository = mockk()
    private val giphy: List<Giphy> = listOf(Giphy("test1", Images(Original("url1"))))

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `gif 리스트를 확인한다`() = runTest {
        val expect = giphy
        coEvery { repository.getGif() } returns flowOf(Result.success(giphy))
        launch {
            viewModel.getGif()
            val actual = viewModel.uiState.value.giphyList
            assertThat(expect).isEqualTo(actual)
        }
    }


    @Test
    fun `gif 리스트 개수 확인`() = runTest {
        val expect = giphy
        coEvery { repository.getGif() } returns flowOf(Result.success(giphy!!))
        launch {
            viewModel.getGif()
            val actual = viewModel.uiState.value.giphyList
            assertThat(expect).isEqualTo(actual)
        }
    }

    @Test
    fun `gif 리스트 초기화 확인`() = runTest {
        coEvery { repository.getGif() } returns flowOf(Result.success(giphy!!))
        viewModel.getGif()
        launch {
            val expect = viewModel.uiState.value.isGiphyInitialized
            assertThat(expect).isTrue()
        }
    }

    @Test
    fun `gif 실패 확인`() = runTest {
        val error = Throwable("테스트 실패")

        coEvery { repository.getGif() } returns flowOf(Result.failure(error))
        viewModel.getGif()
        launch {
            val uiState = viewModel.uiState.value
            assertThat(uiState.giphyList).isNull()
            assertThat(uiState.isGiphyInitialized).isFalse()
            assertThat(error).isEqualTo(uiState.error)
        }
    }
}