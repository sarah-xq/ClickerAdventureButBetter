package com.example.idlealchemist.ui.screens

import com.example.idlealchemist.theme.ClickerTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.idlealchemist.model.GameState
import com.example.idlealchemist.ui.components.ActivePotionsRow
import com.example.idlealchemist.ui.components.ApprenticeUpgrade
import com.example.idlealchemist.ui.components.ClickArea
import com.example.idlealchemist.ui.components.DisplayCurrentPoints
import com.example.idlealchemist.ui.components.ExtraClickUpgrade
import com.example.idlealchemist.ui.components.UnlockBanner

/**
 * Main page for the Clicker Game. Stateless: the game lives in GameRepository, this page only
 * draws [state] and reports taps and purchases upward.
 *
 * AI -> restyled the layout with the alchemy theme, moved the game state into GameRepository so the
 * achievements and shop screens can share it, all original game logic was written by me
 */
@Composable
fun ClickerPage(
    state: GameState,
    onTap: () -> Unit,
    onBuyExtraClick: () -> Unit,
    onBuyApprentice: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            DisplayCurrentPoints(state.points, state.autoGain)

            state.unlockNotice?.let { UnlockBanner(it, Modifier.padding(top = 6.dp)) }
            if (state.activePotions.isNotEmpty()) ActivePotionsRow(state.activePotions)

            ClickArea(clickPower = state.clickGain, onClick = onTap)

            Text(
                text = "UPGRADES",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ClickerTheme.colors.vellum,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )

            ExtraClickUpgrade(
                cost = state.extraClickCost,
                points = state.points,
                clickPower = state.clickPower,
                onClick = onBuyExtraClick
            )

            Spacer(Modifier.height(10.dp))

            ApprenticeUpgrade(
                cost = state.autoCost,
                points = state.points,
                apprenticeCount = state.apprenticeCount,
                onClick = onBuyApprentice
            )
        }
    }
}
