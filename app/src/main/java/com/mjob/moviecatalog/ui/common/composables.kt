package com.mjob.moviecatalog.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ErrorView(message: String, retryMessage: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),

        ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = retryMessage)
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            progress = 0.75f
        )
    }
}

@Composable
fun ClickableCardImage(
    modifier: Modifier,
    id: Int,
    imageUrl: String,
    title: String,
    isFavorite: Boolean,
    toggleFavorite: (Int, Boolean) -> Unit) {
    Card(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    color = Color.Gray,
                    blendMode = BlendMode.Modulate
                )
            )
            Text(
                title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = { toggleFavorite(id, !isFavorite) }) {
                IconFavorite(
                    isFavorite = isFavorite
                )
            }
        }
    }
}

@Composable
fun CardImage(
    modifier: Modifier,
    imageUrl: String,
) {
    Card(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
fun IconFavorite(isFavorite: Boolean) {
    Icon(
        imageVector = if (isFavorite) {
            Icons.Outlined.Favorite
        } else {
            Icons.Outlined.FavoriteBorder
        },
        tint = Color.White,
        contentDescription = null,
    )
}

@Composable
fun VideoContent(videoId: String, modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            val view = YouTubePlayerView(it)
            view.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, 0f)
                    }


                }
            )
            view
        })
}

@Composable
fun x(){

}