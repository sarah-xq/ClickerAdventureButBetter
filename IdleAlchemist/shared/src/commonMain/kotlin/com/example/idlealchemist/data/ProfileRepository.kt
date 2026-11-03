package com.example.idlealchemist.data

import com.example.idlealchemist.model.AlchemistProfile
import com.example.idlealchemist.model.ProfileDraft
import com.example.idlealchemist.model.validateProfileDraft
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Provides the session's shared alchemist collection.
 *
 * Operations are synchronous and must be called from the UI thread.
 * Implementations may permit repeated aliases; IDs identify profiles.
 */
interface ProfileRepository {
    /** Observable, read-only collection shared by the application screens. */
    val profiles: StateFlow<List<AlchemistProfile>>

    /**
     * Validates, normalizes, and stores a new profile.
     *
     * @param draft Input entered on the registration screen.
     * @return The exact profile added to the shared collection.
     * @throws IllegalArgumentException If a required value is invalid.
     */
    fun createProfile(draft: ProfileDraft): AlchemistProfile

    /**
     * Removes a profile from the shared collection.
     *
     * @param profileId Stable identifier of the profile to remove.
     */
    fun removeProfile(profileId: String)
}

/**
 * Keeps profiles in memory for the current application session.
 *
 * The application provider saves a small snapshot for supported
 * saved-state restoration. This implementation does not write to disk.
 *
 * @param initialProfiles Profiles reconstructed from a saved snapshot.
 */
class SessionProfileRepository(
    initialProfiles: List<AlchemistProfile> = emptyList(),
) : ProfileRepository {

    private val mutableProfiles =
        MutableStateFlow(initialProfiles.toList())

    override val profiles: StateFlow<List<AlchemistProfile>> =
        mutableProfiles.asStateFlow()

    init {
        require(
            initialProfiles.map { profile -> profile.id }.distinct().size ==
                    initialProfiles.size
        ) {
            "Restored profile IDs must be unique."
        }
    }

    /**
     * Adds one validated profile and publishes the updated collection.
     *
     * @param draft Editable registration input.
     * @return The newly stored profile.
     * @throws IllegalArgumentException If the draft is invalid.
     */
    override fun createProfile(draft: ProfileDraft): AlchemistProfile {
        val normalized = draft.normalized()

        require(validateProfileDraft(normalized).isValid) {
            "Complete all required profile fields with valid values."
        }

        val profile = AlchemistProfile(
            id = createUniqueId(),
            alias = normalized.alias,
            elementalAffinity = normalized.elementalAffinity,
            motto = normalized.motto,
            avatarUrl = normalized.avatarUrl,
        )

        mutableProfiles.value = mutableProfiles.value + profile
        return profile
    }

    /**
     * Publishes a collection without the requested profile.
     *
     * An unknown identifier leaves the collection unchanged.
     *
     * @param profileId Identifier selected for removal.
     */
    override fun removeProfile(profileId: String) {
        mutableProfiles.value = mutableProfiles.value.filterNot { profile ->
            profile.id == profileId
        }
    }

    /**
     * Generates an identifier absent from the current collection.
     *
     * @return A unique identifier within this repository.
     */
    private fun createUniqueId(): String {
        var candidate: String

        do {
            candidate = "alchemist-" +
                    Random.nextLong().toString(16) +
                    "-" +
                    Random.nextLong().toString(16)
        } while (
            mutableProfiles.value.any { profile -> profile.id == candidate }
        )

        return candidate
    }
}