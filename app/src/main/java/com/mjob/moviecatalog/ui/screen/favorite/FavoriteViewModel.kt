package com.mjob.moviecatalog.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.usecases.GetFavoriteContentsUseCase
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
class FavoriteViewModel @Inject constructor(
    private val getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    private val setFavoriteContentUseCase: SetFavoriteContentUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Content>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Content>>> = _state

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            getFavoriteContentsUseCase.execute()
                .catch {
                    _state.value = UiState.Error(it.message.orEmpty())
                }
                .collect { contents ->
                    _state.value = UiState.Success(
                        contents
                    )
                }
        }
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch() {
            val isMovie = (_state.value as UiState.Success)
                .data
                .firstOrNull { content -> content.id == id }
                ?.isMovie()
                .orFalse()

            setFavoriteContentUseCase.execute(id, isFavorite, isMovie)
                .collect { isUpdateSuccessful ->
                    if (isUpdateSuccessful) {
                        _state.update {
                            (it as UiState.Success<List<Content>>)
                                .copy(
                                data = it.data.filterNot { content -> content.id == id }
                            )
                        }
                    }

                }

        }
    }
}