package likelion.project.giphy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.repository.GiphyRepository

class MainViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun getGif() {
        viewModelScope.launch {
            GiphyRepository.getGif()
                .collect { result ->
                    result.onSuccess { gifs ->
                        _uiState.update {
                            it.copy(
                                giphyList = gifs,
                                isGiphyInitialized = true
                            )
                        }
                    }.onFailure {

                    }
                }
        }
    }
}

data class UIState(
    val giphyList: List<Giphy>? = null,
    val isGiphyInitialized: Boolean = false,
)