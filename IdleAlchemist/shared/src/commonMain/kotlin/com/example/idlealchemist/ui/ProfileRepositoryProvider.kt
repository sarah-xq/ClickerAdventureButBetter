package com.example.idlealchemist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.idlealchemist.data.ProfileRepository
import com.example.idlealchemist.data.SessionProfileRepository
import com.example.idlealchemist.model.AlchemistProfile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Supplies the application-scoped session repository.
 *
 * Route containers may read this provider. Reusable UI components
 * should receive explicit values and callbacks.
 */
val LocalProfileRepository = staticCompositionLocalOf<ProfileRepository> {
    error("ProfileRepositoryProvider must wrap the application screens.")
}

/**
 * Saves the small session collection as a JSON string.
 *
 * This supports platform saved-state restoration and is not a substitute
 * for database storage when the collection becomes large or durable.
 */
private val ProfileRepositorySaver =
    Saver<SessionProfileRepository, String>(
        save = { repository ->
            Json.encodeToString(repository.profiles.value)
        },
        restore = { savedProfiles ->
            SessionProfileRepository(
                initialProfiles =
                    Json.decodeFromString<List<AlchemistProfile>>(
                        savedProfiles
                    ),
            )
        },
    )

/**
 * Creates and provides one shared repository for the wrapped application.
 *
 * @param content Screens and navigation that share the profile collection.
 */
@Composable
fun ProfileRepositoryProvider(
    content: @Composable () -> Unit,
) {
    val repository = rememberSaveable(
        saver = ProfileRepositorySaver,
    ) {
        SessionProfileRepository()
    }

    CompositionLocalProvider(
        LocalProfileRepository provides repository,
        content = content,
    )
}