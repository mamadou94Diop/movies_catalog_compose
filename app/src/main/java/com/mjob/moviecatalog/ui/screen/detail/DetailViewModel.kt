package com.mjob.moviecatalog.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.usecases.GetContentUseCase
import com.mjob.moviecatalog.domain.usecases.SetFavoriteContentUseCase
import com.mjob.moviecatalog.orFalse
import com.mjob.moviecatalog.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getContentUseCase: GetContentUseCase,
    private val setFavoriteContentUseCase: SetFavoriteContentUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<Content>>(UiState.Loading)
    val state: StateFlow<UiState<Content>> = _state

    fun setId(id:Int){
        getContent(id)
    }
    fun getContent(id: Int) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            getContentUseCase.execute(id)
                .catch {
                    print(it.stackTraceToString())
                    _state.value = UiState.Error(it.message ?: "an error occured")
                }.collect {
                    println(it)
                    _state.value = UiState.Success(it)
                }
        }
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch() {
            val isMovie = (_state.value as UiState.Success).data.isMovie.orFalse()
            setFavoriteContentUseCase.execute(id, isFavorite, isMovie)
                .collect { isUpdateSuccessful ->
                    if (isUpdateSuccessful) {
                        _state.update {
                            (it as UiState.Success<Content>).copy(
                                data = it.data.copy(
                                    isFavorite = isFavorite
                                )
                            )
                        }
                    }

                }

        }
    }
}