package com.mjob.moviecatalog.ui.screen.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.ui.common.ErrorView
import com.mjob.moviecatalog.ui.common.LoadingView
import com.mjob.moviecatalog.ui.state.UiState

@Composable
fun DetailScreen(id: Int, viewModel: DetailViewModel, navigationCallback: () -> Boolean) {
    val state: State<UiState<Content>> = viewModel.state.collectAsStateWithLifecycle()

    LocalContext.current
    when (val value = state.value) {
        is UiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
            LoadingView()
        }

        is UiState.Error -> {
            ErrorView(message = value.message,
                retryMessage = "Réessayer",
                onRetry = { viewModel.getContent(id) })
        }

        is UiState.Success -> {
            DetailView(
                content = value.data,
                onClose = navigationCallback,
                onToggleFavorite = { contentId: Int, isFavorite: Boolean ->
                    viewModel.toggleFavorite(
                        contentId,
                        isFavorite
                    )
                }
            )
        }
    }
}