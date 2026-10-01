package com.example.emptyactivity

import com.example.emptyactivity.ui.theme.LightGreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * Displays the current points accumulated from clicking or an auto farm.
 *
 * @param clickCount The number of points accumulated.
 * @param modifier Modifier to apply to this layout.
 *
 * AI -> refactored the code into its own function, all code was written by me
 * **/
@Composable
fun DisplayCurrentPoints(clickCount: Int, modifier: Modifier = Modifier){
    Row(modifier = Modifier.fillMaxWidth().background(color = Color(0xFF639840)).height(100.dp), verticalAlignment = Alignment.CenterVertically){
        Text(
            text = "Current Melon Points $clickCount",
            modifier = modifier
        )
    }
}
/**
 * Main page for the Clicker Game.
 *
 * @param modifier Modifier to apply to this layout.
 *
 * AI -> created LaunchedEffect and refactored code to be in it's own functions
 * all other code was written by me
 * **/
@Composable
fun ClickerPage(modifier: Modifier = Modifier) {
    var clickCount by remember { mutableStateOf(0) }
    var extraClickCount by remember { mutableStateOf(1) }
    var extraClickCost by remember { mutableStateOf(10) }
    var autoCost by remember { mutableStateOf(150) }
    var autoFarmerCount by remember { mutableStateOf(0) }
    val growthFactorExtra = 1.15
    val growthFactorAuto = 1.55

    LaunchedEffect(autoFarmerCount) {
        while (autoFarmerCount > 0) {
            delay(1000L)
            clickCount += autoFarmerCount
        }
    }

    Column(Modifier.fillMaxSize().background(color = Color.White)) {
        DisplayCurrentPoints(clickCount)

        ClickArea(
            modifier = modifier,
            onClick = { clickCount += extraClickCount }
        )

        ExtraClickUpgrade(
            modifier = modifier,
            cost = extraClickCost,
            onClick = {
                if (clickCount >= extraClickCost) {
                    clickCount -= extraClickCost
                    extraClickCost += (extraClickCost * growthFactorExtra).roundToInt()
                    extraClickCount++
                }
            }
        )

        AutoFarmerUpgrade(
            modifier = modifier,
            cost = autoCost,
            onClick = {
                if (clickCount >= autoCost) {
                    clickCount -= autoCost
                    autoCost += (autoCost * growthFactorAuto).roundToInt()
                    autoFarmerCount++
                }
            }
        )
    }
}
/**
 * Click area for the Clicker Game.
 *
 * @param modifier Modifier to apply to this layout.
 * @param onClick Function to execute when the area is clicked.
 *
 *
 * AI -> refactored the code into its own function, all code was written by me
 * ***/
@Composable
fun ColumnScope.ClickArea(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .weight(1f)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(15.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .background(color = LightGreen)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click Me!",
                modifier = modifier
            )
        }
    }
}

/**
 * Extra click upgrade for the Clicker Game.
 *
 * @param modifier Modifier to apply to this layout.
 * @param cost The cost of the upgrade.
 * @param onClick Function to execute when the upgrade is clicked.
 *
 * AI -> refactored the code into its own function, all code was written by me
 * **/
@Composable
fun ExtraClickUpgrade(
    modifier: Modifier = Modifier,
    cost: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF639840))
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(15.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .background(color = LightGreen)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Extra Click Cost: $cost",
                modifier = modifier
            )
        }
    }
}
/**
 * Auto farmer upgrade for the Clicker Game.
 *
 * @param modifier Modifier to apply to this layout.
 * @param cost The cost of the upgrade.
 * @param onClick Function to execute when the upgrade is clicked.
 *
 * AI -> refactored the code into its own function, all code was written by me
 * **/
@Composable
fun AutoFarmerUpgrade(
    modifier: Modifier = Modifier,
    cost: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF639840))
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .background(color = LightGreen)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Auto Farmer Cost: $cost",
                modifier = modifier
            )
        }
    }
}