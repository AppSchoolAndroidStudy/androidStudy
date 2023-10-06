package likelion.project.giphy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import likelion.project.giphy.repository.GiphyRepositoryImpl
import likelion.project.giphy.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(val repository: GiphyRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}