package com.example.emptyactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LoginScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TitleCard()
        UserCreationForm()
    }
}
