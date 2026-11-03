package com.example.idlealchemist.feature.grimoire

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.idlealchemist.model.AlchemistProfile
import com.example.idlealchemist.model.AlchemistTier
import com.example.idlealchemist.ui.ProfileAvatar

/**
 * Displays the session's alchemist compendium.
 *
 * @param state Collection, filter, sort, and selection values.
 * @param onAffinityChange Called with an affinity, or null for all.
 * @param onTierChange Called with a tier, or null for all.
 * @param onSortChange Called when another ordering is selected.
 * @param onToggleProfile Called with the profile ID to expand or collapse.
 * @param onRequestRemoval Called with the profile ID to review for removal.
 * @param onDismissRemoval Called when removal is cancelled.
 * @param onConfirmRemoval Called when removal is confirmed.
 * @param onCreateProfile Called when registration is requested.
 * @param modifier Configuration applied to the screen container.
 */
@Composable
fun GrimoireScreen(
    state: GrimoireUiState,
    onAffinityChange: (String?) -> Unit,
    onTierChange: (AlchemistTier?) -> Unit,
    onSortChange: (GrimoireSort) -> Unit,
    onToggleProfile: (String) -> Unit,
    onRequestRemoval: (String) -> Unit,
    onDismissRemoval: () -> Unit,
    onConfirmRemoval: () -> Unit,
    onCreateProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.background,
                        colors.primaryContainer.copy(alpha = 0.18f),
                        colors.background,
                    ),
                ),
            ),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 960.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(key = "grimoire-heading") {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "Your Grimoire",
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Text(
                        text = "A record of the alchemists who have " +
                                "entered your workshop.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurfaceVariant,
                    )

                    Text(
                        text = "${state.visibleProfiles.size} of " +
                                "${state.totalCount} profiles",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.secondary,
                    )
                }
            }

            if (state.totalCount > 0) {
                item(key = "grimoire-controls") {
                    GrimoireControls(
                        state = state,
                        onAffinityChange = onAffinityChange,
                        onTierChange = onTierChange,
                        onSortChange = onSortChange,
                    )
                }
            }

            when {
                state.totalCount == 0 -> {
                    item(key = "empty-grimoire") {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large),
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(16.dp),
                            ) {
                                Text(
                                    text = "Your grimoire is empty",
                                    style =
                                        MaterialTheme.typography.titleLarge,
                                )

                                Text(
                                    text = "Create an alchemist to begin " +
                                            "your collection.",
                                    style =
                                        MaterialTheme.typography.bodyLarge,
                                )

                                Button(onClick = onCreateProfile) {
                                    Text("Create an alchemist")
                                }
                            }
                        }
                    }
                }

                state.visibleProfiles.isEmpty() -> {
                    item(key = "no-filter-matches") {
                        Column(
                            modifier = Modifier.padding(vertical = 20.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = "No alchemists match these filters.",
                                style = MaterialTheme.typography.bodyLarge,
                            )

                            OutlinedButton(
                                onClick = {
                                    onAffinityChange(null)
                                    onTierChange(null)
                                },
                            ) {
                                Text("Clear filters")
                            }
                        }
                    }
                }

                else -> {
                    items(
                        items = state.visibleProfiles,
                        key = { profile -> profile.id },
                    ) { profile ->
                        GrimoireProfileCard(
                            profile = profile,
                            expanded =
                                state.expandedProfileId == profile.id,
                            onToggle = {
                                onToggleProfile(profile.id)
                            },
                            onRemove = {
                                onRequestRemoval(profile.id)
                            },
                        )
                    }
                }
            }

            if (state.totalCount > 0) {
                item(key = "add-another-alchemist") {
                    OutlinedButton(
                        onClick = onCreateProfile,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        Text("Create another alchemist")
                    }
                }
            }
        }
    }

    state.pendingRemoval?.let { profile ->
        AlertDialog(
            onDismissRequest = onDismissRemoval,
            title = {
                Text("Remove alchemist?")
            },
            text = {
                Text(
                    text = "Remove ${profile.alias} from your grimoire? " +
                            "Their profile will be removed from this session.",
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirmRemoval) {
                    Text(
                        text = "Remove",
                        color = colors.error,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRemoval) {
                    Text("Cancel")
                }
            },
        )
    }
}

/**
 * Displays affinity filters, tier filters, and sort choices.
 *
 * Only the transient open/closed menu state is owned locally.
 *
 * @param state Current filter and ordering values.
 * @param onAffinityChange Called with an affinity or null.
 * @param onTierChange Called with a tier or null.
 * @param onSortChange Called with the selected ordering.
 */
@Composable
private fun GrimoireControls(
    state: GrimoireUiState,
    onAffinityChange: (String?) -> Unit,
    onTierChange: (AlchemistTier?) -> Unit,
    onSortChange: (GrimoireSort) -> Unit,
) {
    var sortMenuExpanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Element: ${state.selectedAffinity ?: "All"}",
                style = MaterialTheme.typography.labelLarge,
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item(key = "all-affinities") {
                    FilterChip(
                        selected = state.selectedAffinity == null,
                        onClick = {
                            onAffinityChange(null)
                        },
                        label = {
                            Text("All elements")
                        },
                    )
                }

                items(
                    items = state.affinities,
                    key = { affinity -> "affinity:$affinity" },
                ) { affinity ->
                    FilterChip(
                        selected = state.selectedAffinity.equals(
                            other = affinity,
                            ignoreCase = true,
                        ),
                        onClick = {
                            onAffinityChange(affinity)
                        },
                        label = {
                            Text(affinity)
                        },
                    )
                }
            }

            Text(
                text = "Tier",
                style = MaterialTheme.typography.labelLarge,
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item(key = "all-tiers") {
                    FilterChip(
                        selected = state.selectedTier == null,
                        onClick = {
                            onTierChange(null)
                        },
                        label = {
                            Text("All tiers")
                        },
                    )
                }

                items(
                    items = AlchemistTier.entries.toList(),
                    key = { tier -> tier.name },
                ) { tier ->
                    FilterChip(
                        selected = state.selectedTier == tier,
                        onClick = {
                            onTierChange(tier)
                        },
                        label = {
                            Text(tier.label)
                        },
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = {
                        sortMenuExpanded = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Sort: ${state.sort.label}")
                }

                DropdownMenu(
                    expanded = sortMenuExpanded,
                    onDismissRequest = {
                        sortMenuExpanded = false
                    },
                ) {
                    GrimoireSort.entries.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(option.label)
                            },
                            onClick = {
                                onSortChange(option)
                                sortMenuExpanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays a profile summary with expandable full details.
 *
 * @param profile Profile from the shared collection.
 * @param expanded Whether full details are visible.
 * @param onToggle Called when the card is selected.
 * @param onRemove Called when removal confirmation is requested.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GrimoireProfileCard(
    profile: AlchemistProfile,
    expanded: Boolean,
    onToggle: () -> Unit,
    onRemove: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    Card(
        onClick = onToggle,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.outline, MaterialTheme.shapes.large)
            .animateContentSize(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = colors.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = profile.alias,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = "${profile.elementalAffinity} · " +
                        profile.tier.label,
                style = MaterialTheme.typography.labelLarge,
                color = colors.secondary,
            )

            Text(
                text = "Production: " +
                        "${profile.productionRatePerSecond} essence/s",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant,
            )

            Text(
                text = if (expanded) {
                    "Hide details"
                } else {
                    "Show details"
                },
                style = MaterialTheme.typography.labelLarge,
                color = colors.primary,
            )

            if (expanded) {
                HorizontalDivider()

                ProfileAvatar(
                    avatarUrl = profile.avatarUrl,
                    alias = profile.alias,
                )

                Text(
                    text = "Motto or specialty",
                    style = MaterialTheme.typography.labelLarge,
                )

                Text(
                    text = profile.motto,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Avatar or sigil URL",
                    style = MaterialTheme.typography.labelLarge,
                )

                Text(
                    text = profile.avatarUrl,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant,
                )

                Text(
                    text = "Profile ID",
                    style = MaterialTheme.typography.labelLarge,
                )

                Text(
                    text = profile.id,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant,
                )

                TextButton(onClick = onRemove) {
                    Text(
                        text = "Remove alchemist",
                        color = colors.error,
                    )
                }
            }
        }
    }
}