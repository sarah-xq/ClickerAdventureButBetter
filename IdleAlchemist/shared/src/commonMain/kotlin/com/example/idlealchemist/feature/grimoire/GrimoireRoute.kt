package com.example.idlealchemist.feature.grimoire

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.idlealchemist.model.AlchemistProfile
import com.example.idlealchemist.model.AlchemistTier
import com.example.idlealchemist.ui.LocalProfileRepository
import kotlin.collections.asReversed

/**
 * Connects the grimoire to the application-scoped profile repository.
 *
 * Filters and selections use saveable primitive values. The profile
 * collection remains owned by the repository.
 *
 * @param onCreateProfile Called when registration is requested.
 * @param onProfileRemoved Called after removal with the removed profile ID.
 * @param modifier Layout configuration supplied by the application.
 */
@Composable
fun GrimoireRoute(
    onCreateProfile: () -> Unit,
    onProfileRemoved: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val repository = LocalProfileRepository.current
    val profiles by repository.profiles.collectAsState()

    var selectedAffinity by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var selectedTierName by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var sortName by rememberSaveable {
        mutableStateOf(GrimoireSort.NEWEST_FIRST.name)
    }

    var expandedProfileId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var pendingRemovalId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val selectedTier = AlchemistTier.entries.firstOrNull { tier ->
        tier.name == selectedTierName
    }

    val sort = GrimoireSort.entries.firstOrNull { option ->
        option.name == sortName
    } ?: GrimoireSort.NEWEST_FIRST

    val affinities = profiles
        .map { profile -> profile.elementalAffinity }
        .distinctBy { affinity -> affinity.lowercase() }
        .sortedBy { affinity -> affinity.lowercase() }

    val visibleProfiles = filterAndSortProfiles(
        profiles = profiles,
        affinity = selectedAffinity,
        tier = selectedTier,
        sort = sort,
    )

    val pendingRemoval = profiles.firstOrNull { profile ->
        profile.id == pendingRemovalId
    }

    val state = GrimoireUiState(
        totalCount = profiles.size,
        visibleProfiles = visibleProfiles,
        affinities = affinities,
        selectedAffinity = selectedAffinity,
        selectedTier = selectedTier,
        sort = sort,
        expandedProfileId = expandedProfileId,
        pendingRemoval = pendingRemoval,
    )

    GrimoireScreen(
        state = state,
        onAffinityChange = { affinity ->
            selectedAffinity = affinity
        },
        onTierChange = { tier ->
            selectedTierName = tier?.name
        },
        onSortChange = { option ->
            sortName = option.name
        },
        onToggleProfile = { profileId ->
            expandedProfileId = if (expandedProfileId == profileId) {
                null
            } else {
                profileId
            }
        },
        onRequestRemoval = { profileId ->
            pendingRemovalId = profileId
        },
        onDismissRemoval = {
            pendingRemovalId = null
        },
        onConfirmRemoval = {
            pendingRemovalId?.let { profileId ->
                repository.removeProfile(profileId)

                if (expandedProfileId == profileId) {
                    expandedProfileId = null
                }

                pendingRemovalId = null
                onProfileRemoved(profileId)
            }
        },
        onCreateProfile = onCreateProfile,
        modifier = modifier,
    )
}

/**
 * Filters profiles and returns a newly ordered list.
 *
 * Creation order follows repository insertion order. Affinity matching
 * ignores case. Production-rate ties are resolved by alias and ID.
 *
 * @param profiles Complete collection in creation order.
 * @param affinity Affinity to match, or null to accept all.
 * @param tier Tier to match, or null to accept all.
 * @param sort Ordering applied after filtering.
 * @return Profiles matching both filters in the requested order.
 */
private fun filterAndSortProfiles(
    profiles: List<AlchemistProfile>,
    affinity: String?,
    tier: AlchemistTier?,
    sort: GrimoireSort,
): List<AlchemistProfile> {
    val filtered = profiles.filter { profile ->
        val matchesAffinity = affinity == null ||
                profile.elementalAffinity.equals(
                    other = affinity,
                    ignoreCase = true,
                )

        val matchesTier = tier == null || profile.tier == tier

        matchesAffinity && matchesTier
    }

    return when (sort) {
        GrimoireSort.NEWEST_FIRST -> filtered.asReversed()

        GrimoireSort.OLDEST_FIRST -> filtered

        GrimoireSort.ALIAS_ASCENDING -> filtered.sortedWith(
            compareBy<AlchemistProfile> { profile ->
                profile.alias.lowercase()
            }.thenBy { profile ->
                profile.id
            },
        )

        GrimoireSort.PRODUCTION_DESCENDING -> filtered.sortedWith(
            compareByDescending<AlchemistProfile> { profile ->
                profile.productionRatePerSecond
            }.thenBy { profile ->
                profile.alias.lowercase()
            }.thenBy { profile ->
                profile.id
            },
        )
    }
}