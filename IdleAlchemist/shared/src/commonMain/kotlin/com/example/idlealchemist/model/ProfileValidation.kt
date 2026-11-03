package com.example.idlealchemist.model

import io.ktor.http.URLProtocol
import io.ktor.http.Url

/**
 * Stores field-specific validation messages.
 *
 * A null message means that the corresponding field is valid.
 *
 * @property aliasError Validation message for the alias.
 * @property affinityError Validation message for the elemental affinity.
 * @property mottoError Validation message for the motto or specialty.
 * @property avatarUrlError Validation message for the avatar URL.
 */
data class ProfileValidation(
    val aliasError: String? = null,
    val affinityError: String? = null,
    val mottoError: String? = null,
    val avatarUrlError: String? = null,
) {
    /** Whether every required field satisfies its validation rule. */
    val isValid: Boolean
        get() = aliasError == null &&
                affinityError == null &&
                mottoError == null &&
                avatarUrlError == null
}

/**
 * Validates a profile draft without changing it.
 *
 * Surrounding whitespace is ignored. URL validation checks syntax only;
 * image availability and image decoding belong to the receiving screen.
 *
 * @param draft Editable values to validate.
 * @return Field-specific errors and the overall validity result.
 */
fun validateProfileDraft(draft: ProfileDraft): ProfileValidation {
    val normalized = draft.normalized()

    return ProfileValidation(
        aliasError = if (normalized.alias.isBlank()) {
            "Enter your alchemist alias."
        } else {
            null
        },
        affinityError = if (normalized.elementalAffinity.isBlank()) {
            "Enter an elemental affinity."
        } else {
            null
        },
        mottoError = if (normalized.motto.isBlank()) {
            "Enter a motto or specialty."
        } else {
            null
        },
        avatarUrlError = when {
            normalized.avatarUrl.isBlank() ->
                "Enter an avatar or sigil URL."

            !isValidAvatarUrl(normalized.avatarUrl) ->
                "Use a complete HTTPS URL, such as https://example.com/sigil.png"

            else -> null
        },
    )
}

/**
 * Checks whether an address has an explicit HTTPS scheme and host.
 *
 * Whitespace and embedded credentials are rejected. A successful result
 * does not establish that the address returns an image.
 *
 * @param value Trimmed address to inspect.
 * @return True when the address satisfies this form's URL rules.
 */
private fun isValidAvatarUrl(value: String): Boolean {
    if (!value.startsWith("https://", ignoreCase = true)) {
        return false
    }

    if (value.any { character -> character.isWhitespace() }) {
        return false
    }

    val authority = value
        .substringAfter("://")
        .substringBefore('/')
        .substringBefore('?')
        .substringBefore('#')

    if (authority.isBlank() || '@' in authority) {
        return false
    }

    val parsedUrl = runCatching { Url(value) }.getOrNull()
        ?: return false

    return parsedUrl.protocol == URLProtocol.HTTPS &&
            parsedUrl.host.isNotBlank()
}