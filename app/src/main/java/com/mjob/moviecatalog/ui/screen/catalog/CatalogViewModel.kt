package com.mjob.moviecatalog.ui.screen.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjob.moviecatalog.addOrRemove
import com.mjob.moviecatalog.domain.model.Catalog
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.model.ContentGroup
import com.mjob.moviecatalog.domain.usecases.GetContentsUseCase
import com.mjob.moviecatalog.domain.usecases.SetFavoriteContentUseCase
import com.mjob.moviecatalog.orFalse
import com.mjob.moviecatalog.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getContentsUseCase: GetContentsUseCase,
    private val setFavoriteContentUseCase: SetFavoriteContentUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow<UiState<Catalog>>(UiState.Loading)
    val state: StateFlow<UiState<Catalog>> = _state

    init {
        getContent()
    }

    fun getContent() {
        viewModelScope.launch() {
            Log.d("diop","getContent()")

            getContentsUseCase.execute()
                .collect {
                    if (it.isSuccess) {
                        val contents = it.getOrNull()
                        val favorites = contents.orEmpty()
                            .filter { content -> content.isFavorite }
                            .map { content -> content.id }
                        val contentsGroups = contents
                            .orEmpty()
                            .groupBy { it.genre }
                            .map { entry: Map.Entry<String, List<Content>> ->
                                ContentGroup(name = entry.key, contents = entry.value)
                            }.sortedBy { contentGroup -> contentGroup.name }
                        _state.value = UiState.Success(
                            Catalog(
                                favorites = favorites,
                                contentsGroups = contentsGroups
                            )
                        )
                    } else {
                        val message = it.exceptionOrNull()?.message.orEmpty()
                        _state.value = UiState.Error(message)
                    }

                }
        }
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch() {
            val isMovie = (_state.value as UiState.Success)
                .data
                .contentsGroups
                .map { it.contents }
                .flatten()
                .firstOrNull { it.id == id }
                ?.isMovie()
                .orFalse()

            setFavoriteContentUseCase.execute(id, isFavorite, isMovie)
                .collect { isUpdateSuccessful ->
                    if (isUpdateSuccessful){
                        _state.update {
                            (it as UiState.Success<Catalog>).copy(data = it.data.copy(favorites = it.data.favorites.addOrRemove(id) ))
                        }
                    }

                }
        }
    }
}
