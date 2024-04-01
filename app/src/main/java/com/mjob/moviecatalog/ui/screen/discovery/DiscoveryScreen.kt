package com.mjob.moviecatalog.ui.screen.discovery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mjob.moviecatalog.domain.model.Catalog
import com.mjob.moviecatalog.domain.model.Trailer
import com.mjob.moviecatalog.ui.common.ErrorView
import com.mjob.moviecatalog.ui.common.LoadingView
import com.mjob.moviecatalog.ui.common.VideoContent
import com.mjob.moviecatalog.ui.screen.catalog.CatalogView
import com.mjob.moviecatalog.ui.screen.catalog.CatalogViewModel
import com.mjob.moviecatalog.ui.state.UiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoveryScreen(viewModel: TrailerViewModel) {
    val state: State<UiState<List<Trailer>>> = viewModel.state.collectAsStateWithLifecycle()

    when (val value = state.value) {
        is UiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
            LoadingView()
        }


        is UiState.Error -> {
            ErrorView(message = value.message,
                retryMessage = "Réessayer",
                onRetry = { viewModel.getTrailers() })
        }

        is UiState.Success -> {
            val pagerState = rememberPagerState(pageCount = {
                value.data.size
            })
            VerticalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
                VideoContent(
                    videoId = value.data[page].url!!,
                    modifier = Modifier.fillMaxSize()

                )
            }
        }
    }
}