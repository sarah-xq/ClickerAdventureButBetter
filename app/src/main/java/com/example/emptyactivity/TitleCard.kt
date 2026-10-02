package com.example.emptyactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.ui.theme.Green01
import com.example.emptyactivity.ui.theme.Green02

/**
 * The main top-level hero banner for the game application.
 *
 * Displays the bold primary title **"Clicker Adventure"** centered within a high-contrast
 * rounded [Card]. Configured with substantial top margin padding (`50.dp`) to separate
 * the header from system UI bars and status areas.
 *
 * @see UserCreationPanel
 */
@Composable
fun TitleCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Green01,
            contentColor = Green02
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 50.dp, 16.dp, 32.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = "Clicker Adventure",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}