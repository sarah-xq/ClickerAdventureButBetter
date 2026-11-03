package com.example.idlealchemist.feature.trail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.idlealchemist.model.AlchemistProfile
import com.example.idlealchemist.model.TrailStage
import com.example.idlealchemist.ui.AlchemistProfileCard

/**
 * Displays the player summary and numbered challenge trail.
 *
 * Every stage can be inspected. This screen does not claim completion
 * or modify player progress when a stage is selected.
 *
 * @param profile Player snapshot received through navigation.
 * @param stages Ordered challenge definitions with unique stage numbers.
 * @param selectedStage Challenge currently being inspected, or null.
 * @param onStageSelected Called when a stage node or card is selected.
 * @param onDismissStage Called when the challenge dialog is closed.
 * @param onCreateAnother Called to return to profile creation.
 * @param modifier Configuration applied to the screen container.
 */
@Composable
fun CrucibleTrailScreen(
    profile: AlchemistProfile,
    stages: List<TrailStage>,
    selectedStage: TrailStage?,
    onStageSelected: (TrailStage) -> Unit,
    onDismissStage: () -> Unit,
    onCreateAnother: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.background,
                        colors.secondaryContainer.copy(alpha = 0.20f),
                        colors.background,
                    ),
                ),
            ),
        contentAlignment = Alignment.TopCenter,
    ) {
        val horizontalPadding = if (maxWidth >= 720.dp) {
            32.dp
        } else {
            16.dp
        }

        LazyColumn(
            modifier = Modifier
                .widthIn(max = 960.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = horizontalPadding,
                vertical = 24.dp,
            ),
        ) {
            item(key = "player-summary") {
                AlchemistProfileCard(
                    profile = profile,
                    modifier = Modifier.padding(bottom = 28.dp),
                )
            }

            item(key = "trail-introduction") {
                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "The Crucible Trail",
                        style = MaterialTheme.typography.headlineLarge,
                        color = colors.onBackground,
                    )

                    Text(
                        text = "From your first spark to the great " +
                                "transmutation, every stage offers a new " +
                                "alchemical challenge.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurfaceVariant,
                    )

                    Text(
                        text = "${stages.size} stages · Select a stage " +
                                "to inspect its objective",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondary,
                    )
                }
            }

            if (stages.isEmpty()) {
                item(key = "empty-trail") {
                    Text(
                        text = "No stages are available yet.",
                        modifier = Modifier.padding(vertical = 20.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            itemsIndexed(
                items = stages,
                key = { _, stage -> stage.number },
            ) { index, stage ->
                StagePathItem(
                    stage = stage,
                    isLast = index == stages.lastIndex,
                    isSelected = selectedStage?.number == stage.number,
                    onClick = {
                        onStageSelected(stage)
                    },
                )
            }

            item(key = "create-another-profile") {
                OutlinedButton(
                    onClick = onCreateAnother,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 14.dp,
                    ),
                ) {
                    Text("Create another alchemist")
                }
            }
        }
    }

    if (selectedStage != null) {
        StageDetailsDialog(
            stage = selectedStage,
            onDismiss = onDismissStage,
        )
    }
}

/**
 * Displays one numbered node, its connecting line, and its challenge card.
 *
 * Intrinsic row height lets the connector follow the card's text height.
 * Text may wrap without requiring a fixed card height.
 *
 * @param stage Challenge definition to display.
 * @param isLast Whether the connector should end at this node.
 * @param isSelected Whether this stage is currently being inspected.
 * @param onClick Called when the node or card is selected.
 * @param modifier Configuration applied to the complete path item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StagePathItem(
    stage: TrailStage,
    isLast: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        Column(
            modifier = Modifier
                .width(56.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FilledTonalButton(
                onClick = onClick,
                modifier = Modifier
                    .size(52.dp)
                    .semantics {
                        contentDescription =
                            "Inspect stage ${stage.number}: ${stage.title}"
                    },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = stage.number.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(2.dp)
                        .weight(1f)
                        .background(
                            colors.primary.copy(alpha = 0.45f),
                        ),
                )
            }
        }

        Card(
            onClick = onClick,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 12.dp,
                    bottom = 20.dp,
                ),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(
                width = 1.dp,
                color = if (isSelected) {
                    colors.primary
                } else {
                    colors.outline.copy(alpha = 0.35f)
                },
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) {
                    colors.primaryContainer
                } else {
                    colors.surface
                },
            ),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "STAGE ${stage.number} · ${stage.element}",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.secondary,
                )

                Text(
                    text = stage.title,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = stage.objective,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurfaceVariant,
                )

                Text(
                    text = "Inspect challenge",
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.primary,
                )
            }
        }
    }
}

/**
 * Displays the full objective and associated reward for a selected stage.
 *
 * @param stage Challenge selected from the visual trail.
 * @param onDismiss Called when the dialog is dismissed or Close is selected.
 */
@Composable
private fun StageDetailsDialog(
    stage: TrailStage,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Stage ${stage.number}: ${stage.title}",
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Element: ${stage.element}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Text(
                    text = "Objective",
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = stage.objective,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Reward",
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = stage.reward,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
    )
}