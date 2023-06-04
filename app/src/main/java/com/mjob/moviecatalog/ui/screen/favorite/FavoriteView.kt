package com.mjob.moviecatalog.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.ui.common.ClickableCardImage

@Composable
fun FavoriteView(
    contents: List<Content>, onToggleFavorite: (Int, Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contents) { content ->
            ClickableCardImage(
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
                    ,
                id = content.id,
                imageUrl = content.backdropPath,
                title = content.title,
                isFavorite = true,
                toggleFavorite = onToggleFavorite,
            )
        }
    }

}