package com.example.idlealchemist.feature.grimoire

import com.example.idlealchemist.model.AlchemistProfile
import com.example.idlealchemist.model.AlchemistTier

/**
 * Defines the ordering choices for the profile collection.
 *
 * @property label User-facing description of the ordering.
 */
enum class GrimoireSort(
    val label: String,
) {
    NEWEST_FIRST("Newest first"),
    OLDEST_FIRST("Oldest first"),
    ALIAS_ASCENDING("Alias: A to Z"),
    PRODUCTION_DESCENDING("Production: highest first"),
}

/**
 * Stores the values required to render the grimoire.
 *
 * @property totalCount Number of profiles before filtering.
 * @property visibleProfiles Filtered and sorted profiles.
 * @property affinities Distinct available elemental affinities.
 * @property selectedAffinity Active affinity filter, or null for all.
 * @property selectedTier Active tier filter, or null for all.
 * @property sort Current ordering choice.
 * @property expandedProfileId Expanded profile identifier, or null.
 * @property pendingRemoval Profile awaiting removal confirmation, or null.
 */
data class GrimoireUiState(
    val totalCount: Int,
    val visibleProfiles: List<AlchemistProfile>,
    val affinities: List<String>,
    val selectedAffinity: String?,
    val selectedTier: AlchemistTier?,
    val sort: GrimoireSort,
    val expandedProfileId: String?,
    val pendingRemoval: AlchemistProfile?,
)