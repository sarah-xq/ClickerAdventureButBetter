package com.example.idlealchemist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

/**
 * Displays a remotely loaded player avatar with loading and error states.
 *
 * @param avatarUrl HTTPS image address received in the player profile.
 * @param alias Player name used for accessibility and the fallback initial.
 * @param modifier Configuration applied to the avatar container.
 */
@Composable
fun ProfileAvatar(
    avatarUrl: String,
    alias: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalPlatformContext.current

    val request = remember(context, avatarUrl) {
        ImageRequest.Builder(context)
            .data(avatarUrl)
            .crossfade(true)
            .build()
    }

    SubcomposeAsyncImage(
        model = request,
        contentDescription = "Avatar or sigil for $alias",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    strokeWidth = 3.dp,
                )
            }
        },
        error = {
            AvatarFallback(alias = alias)
        },
    )
}

/**
 * Displays a player initial when a remote avatar cannot be loaded.
 *
 * @param alias Player name from which the initial is derived.
 */
@Composable
private fun AvatarFallback(
    alias: String,
) {
    val initial = alias.trim().take(1).uppercase().ifEmpty { "A" }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .semantics {
                contentDescription =
                    "Avatar unavailable. Showing the initial for $alias."
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}