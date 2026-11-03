package com.example.idlealchemist.model

import kotlinx.serialization.Serializable

/**
 * Defines the progression tiers available to alchemists.
 *
 * @property label User-facing tier name.
 */
@Serializable
enum class AlchemistTier(
    val label: String,
) {
    APPRENTICE("Apprentice"),
    ADEPT("Adept"),
    MASTER("Master"),
}

/**
 * Stores a registered alchemist and their progression metadata.
 *
 * @property id Stable identifier independent of the display alias.
 * @property alias Alchemist's trimmed display name.
 * @property elementalAffinity Alchemist's chosen element.
 * @property motto Alchemist's motto or specialty.
 * @property avatarUrl HTTPS address of the avatar or sigil.
 * @property tier Current progression tier.
 * @property productionRatePerSecond Passive essence production per second.
 */
@Serializable
data class AlchemistProfile(
    val id: String,
    val alias: String,
    val elementalAffinity: String,
    val motto: String,
    val avatarUrl: String,
    val tier: AlchemistTier = AlchemistTier.APPRENTICE,
    val productionRatePerSecond: Double = 0.0,
) {
    init {
        require(
            productionRatePerSecond.isFinite() &&
                    productionRatePerSecond >= 0.0
        ) {
            "Production rate must be finite and nonnegative."
        }
    }
}

/**
 * Stores editable profile input before registration.
 *
 * @property alias Alias exactly as entered.
 * @property elementalAffinity Affinity exactly as entered.
 * @property motto Motto or specialty exactly as entered.
 * @property avatarUrl Avatar or sigil URL exactly as entered.
 */
@Serializable
data class ProfileDraft(
    val alias: String = "",
    val elementalAffinity: String = "",
    val motto: String = "",
    val avatarUrl: String = "",
) {
    /**
     * Removes surrounding whitespace before registration.
     *
     * @return A normalized copy of the draft.
     */
    fun normalized(): ProfileDraft = copy(
        alias = alias.trim(),
        elementalAffinity = elementalAffinity.trim(),
        motto = motto.trim(),
        avatarUrl = avatarUrl.trim(),
    )
}