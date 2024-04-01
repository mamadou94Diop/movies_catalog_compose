package com.mjob.moviecatalog.ui.screen.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjob.moviecatalog.domain.model.Catalog
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.model.ContentGroup
import com.mjob.moviecatalog.domain.model.Trailer
import com.mjob.moviecatalog.domain.usecases.GetContentTrailersUseCase
import com.mjob.moviecatalog.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val useCase: GetContentTrailersUseCase
)  : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Trailer>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Trailer>>> = _state

    init {
        getTrailers()
    }

    fun getTrailers() {
        viewModelScope.launch() {
            useCase.execute()
                .collect {
                    if (it.isSuccess) {
                        _state.value = UiState.Success(it.getOrNull().orEmpty())
                    } else {
                        print(it.exceptionOrNull()?.stackTraceToString())
                        val message = it.exceptionOrNull()?.message.orEmpty()
                        _state.value = UiState.Error(message)
                    }

                }
        }
    }
}