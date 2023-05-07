package com.mjob.moviecatalog.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjob.moviecatalog.domain.model.Catalog
import com.mjob.moviecatalog.domain.model.ContentGroup
import com.mjob.moviecatalog.ui.common.ErrorView
import com.mjob.moviecatalog.ui.common.LoadingView
import com.mjob.moviecatalog.ui.state.UiState

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state: State<UiState<Catalog>> =
        viewModel.state.collectAsStateWithLifecycle()
    when (val value = state.value) {
        is UiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
            LoadingView() {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            ErrorView(message = value.message,
                retryMessage = "Réessayer",
                onRetry = { viewModel.getContent() })
        }

        is UiState.Success -> {
            CatalogView(
                contentsGroups = value.data.contentsGroups,
                favorites = value.data.favorites,
            ) { id, isFavorite -> viewModel.toggleFavorite(id, isFavorite) }
        }
    }
}