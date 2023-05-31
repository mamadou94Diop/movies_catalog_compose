package com.mjob.moviecatalog.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjob.moviecatalog.ui.common.ErrorView
import com.mjob.moviecatalog.ui.common.LoadingView
import com.mjob.moviecatalog.ui.state.UiState

@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel) {
   val state = viewModel.state.collectAsStateWithLifecycle()

    when (val value = state.value) {
        is UiState.Loading -> LoadingView()


        is UiState.Error -> {
            ErrorView(message = value.message,
                retryMessage = "Réessayer",
                onRetry = { viewModel.getFavorites() })
        }

        is UiState.Success -> {
            if (value.data.isEmpty()){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    Text(text = "No favorite content yet. ")
                }
            } else {
                FavoriteView(
                    contents = value.data,
                ) { id, isFavorite -> viewModel.toggleFavorite(id, isFavorite) }
            }
        }
    }

}