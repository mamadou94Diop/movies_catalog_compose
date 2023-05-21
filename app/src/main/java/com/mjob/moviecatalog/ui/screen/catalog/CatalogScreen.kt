package com.mjob.moviecatalog.ui.screen.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjob.moviecatalog.domain.model.Catalog
import com.mjob.moviecatalog.ui.common.ErrorView
import com.mjob.moviecatalog.ui.common.LoadingView
import com.mjob.moviecatalog.ui.state.UiState

@Composable
fun HomeScreen(viewModel: CatalogViewModel, navigationCallback: (Int) -> Unit) {
    val state: State<UiState<Catalog>> = viewModel.state.collectAsStateWithLifecycle()

    when (val value = state.value) {
        is UiState.Loading -> LoadingView()


        is UiState.Error -> {
            ErrorView(message = value.message,
                retryMessage = "Réessayer",
                onRetry = { viewModel.getContent() })
        }

        is UiState.Success -> {
            CatalogView(
                contentsGroups = value.data.contentsGroups,
                favorites = value.data.favorites,
                onTap = navigationCallback
            ) { id, isFavorite -> viewModel.toggleFavorite(id, isFavorite) }
        }
    }
}