package com.example.idlealchemist.ui

import com.example.idlealchemist.model.AlchemistProfile


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays the active alchemist received through navigation.
 *
 * The avatar and identity stack vertically when the card becomes narrow.
 *
 * @param profile Profile snapshot passed from Screen 1.
 * @param modifier Layout configuration applied to the card.
 */
@Composable
fun AlchemistProfileCard(
    profile: AlchemistProfile,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "ACTIVE ALCHEMIST",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (maxWidth < 340.dp) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        ProfileAvatar(
                            avatarUrl = profile.avatarUrl,
                            alias = profile.alias,
                        )

                        ProfileIdentity(profile = profile)
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        ProfileAvatar(
                            avatarUrl = profile.avatarUrl,
                            alias = profile.alias,
                        )

                        ProfileIdentity(
                            profile = profile,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            Text(
                text = profile.motto,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/**
 * Displays a player's name and elemental affinity.
 *
 * @param profile Player identity to display.
 * @param modifier Layout configuration applied to the identity column.
 */
@Composable
private fun ProfileIdentity(
    profile: AlchemistProfile,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = profile.alias,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = profile.elementalAffinity,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp,
                ),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}