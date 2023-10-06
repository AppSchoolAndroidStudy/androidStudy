package likelion.project.giphy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.repository.GiphyRepository
import likelion.project.giphy.repository.GiphyRepositoryImpl

class MainViewModel(private val giphyRepository: GiphyRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun getGif() {
        viewModelScope.launch {
            giphyRepository.getGif()
                .collect { result ->
                    result.onSuccess { gifs ->
                        _uiState.update {
                            it.copy(
                                giphyList = gifs,
                                isGiphyInitialized = true,
                                error = null
                            )
                        }
                    }.onFailure { error ->
                        _uiState.update {
                            it.copy(
                                giphyList = null,
                                isGiphyInitialized = false,
                                error = error
                            )
                        }
                    }
                }
        }
    }
}

data class UIState(
    val giphyList: List<Giphy>? = null,
    val isGiphyInitialized: Boolean = false,
    val error: Throwable? = null
)