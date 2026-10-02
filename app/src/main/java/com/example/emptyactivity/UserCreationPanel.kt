package com.example.emptyactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.ui.theme.Green01
import com.example.emptyactivity.ui.theme.Green03

/**
 * A stylized header banner for the user registration flow.
 *
 * Renders a full-width rounded [Card] container using the primary brand green palette
 * (`Green01` container with `Green03` text) with a centered "User Creation" title.
 * Designed primarily as a visual anchor inside [UserCreationForm].
 *
 * @see TitleCard
 */
@Composable
fun UserCreationPanel() {
    Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
    containerColor = Green01,
    contentColor = Green03
    ),
    modifier = Modifier
    .fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 32.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(
                text = "User Creation",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}