package com.example.idlealchemist.feature.trail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.idlealchemist.model.AlchemistProfile

/**
 * Owns the selected challenge for the Crucible Trail screen.
 *
 * The profile comes directly from the navigation argument. Only the
 * selected stage number is saved; stage definitions remain in the catalog.
 *
 * @param profile Player snapshot passed from the onboarding route.
 * @param onCreateAnother Called when the player returns to registration.
 * @param modifier Layout configuration supplied by the application.
 */
@Composable
fun CrucibleTrailRoute(
    profile: AlchemistProfile,
    onCreateAnother: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedStageNumber by rememberSaveable(profile.id) {
        mutableStateOf<Int?>(null)
    }

    val selectedStage = CrucibleTrailStages.firstOrNull { stage ->
        stage.number == selectedStageNumber
    }

    CrucibleTrailScreen(
        profile = profile,
        stages = CrucibleTrailStages,
        selectedStage = selectedStage,
        onStageSelected = { stage ->
            selectedStageNumber = stage.number
        },
        onDismissStage = {
            selectedStageNumber = null
        },
        onCreateAnother = onCreateAnother,
        modifier = modifier,
    )
}