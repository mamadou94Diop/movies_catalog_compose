package com.mjob.moviecatalog.ui.screen.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.model.ContentGroup
import com.mjob.moviecatalog.ui.common.ClickableCardImage

@Composable
fun CatalogView(
    contentsGroups: List<ContentGroup>,
    favorites: List<Int>,
    onTap: (Int) -> Unit,
    onToggleFavorite: (Int, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contentsGroups) { contentGroup ->
            Column() {
                Text(text = contentGroup.name, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(8.dp))
                ContentListView(contentGroup.contents, favorites, onTap, onToggleFavorite)
            }
        }
    }
}

@Composable
fun ContentListView(
    contents: List<Content>,
    favorites: List<Int>,
    onTap: (Int) -> Unit,
    onToggleFavorite: (Int, Boolean) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(contents) { content ->
            val isFavorite = favorites.contains(content.id)
            ClickableCardImage(
                modifier = Modifier
                    .height(144.dp)
                    .width(260.dp)
                    .clickable(
                        enabled = true,
                        onClick = {
                            onTap.invoke(content.id)
                        }
                    ),
                id = content.id,
                imageUrl = content.backdropPath,
                title = content.title,
                isFavorite = isFavorite,
                toggleFavorite = onToggleFavorite,
            )
        }
    }
}