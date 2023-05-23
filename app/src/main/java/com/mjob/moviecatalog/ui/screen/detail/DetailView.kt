package com.mjob.moviecatalog.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.model.Episode
import com.mjob.moviecatalog.domain.model.Season
import com.mjob.moviecatalog.ui.common.CardImage
import com.mjob.moviecatalog.ui.common.IconFavorite
import com.mjob.moviecatalog.ui.common.VideoContent

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailView(content: Content, onClose: () -> Boolean, onToggleFavorite: (Int, Boolean) -> Unit) {
    val contentTypeState = remember { mutableStateOf(content.isMovie()) }
    val releaseLabel = if (contentTypeState.value) {
        "Release date : "
    } else {
        "First aired : "
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = content.backdropPath,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(
                        color = Color.Gray,
                        blendMode = BlendMode.Modulate
                    )
                )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    onClick = { onToggleFavorite(content.id, !content.isFavorite) }) {
                    IconFavorite(
                        isFavorite = content.isFavorite
                    )
                }
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        tint = Color.White,
                        contentDescription = null,
                    )
                }
            }

        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = content.title,
                            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                        )
                        Text(text = content.rating)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = content.overview)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = releaseLabel, style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = content.release)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!contentTypeState.value) {
                        SeasonsView(content.seasons.orEmpty())
                    }

                    Text(text = "Trailer", style = TextStyle(fontWeight = FontWeight.Bold))

                    VideoContent(
                        videoId = content.trailer!!,
                        modifier = Modifier.wrapContentHeight()
                    )
                }
            }

        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonsView(seasons: List<Season>) {
    var seasonState by remember { mutableStateOf(seasons.first()) }
    var expandedState by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.width(144.dp),
        expanded = expandedState,
        onExpandedChange = { expandedState = !expandedState }) {
        TextField(
            value = seasonState.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { TrailingIcon(expanded = expandedState) },
            modifier = Modifier.menuAnchor()
        )
        Spacer(modifier = Modifier.height(4.dp))

        ExposedDropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false },
        ) {
            seasons.forEach { season ->
                DropdownMenuItem(text = { Text(season.name) }, onClick = {
                    expandedState = false
                    seasonState = season
                }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
    EpisodesView(episodes = seasonState.episodes)
}

@Composable
fun EpisodesView(
    episodes: List<Episode>,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(episodes) { episode ->
            Column(modifier = Modifier.width(260.dp)) {
                CardImage(
                    modifier = Modifier
                        .height(144.dp)
                        .fillMaxWidth(),
                    imageUrl = episode.thumbnailPath
                )
                Text(
                    text = episode.title,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "First aired : ",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(text = episode.firstAired)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}