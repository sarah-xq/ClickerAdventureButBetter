package com.example.emptyactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.ui.theme.Green01
import com.example.emptyactivity.ui.theme.Green02
import com.example.emptyactivity.ui.theme.Green03
import com.example.emptyactivity.ui.theme.Green04
import com.example.emptyactivity.ui.theme.Pink01
import com.example.emptyactivity.ui.theme.Pink03

/**
 * A form composable that manages user registration and post-signup confirmation state.
 *
 * Handles client-side validation requirements before allowing submission:
 * - **Username:** Must not be blank.
 * - **Password:** Must be at least 8 characters long.
 *
 * Uses [rememberSaveable] to retain input values across configuration changes
 * (such as screen rotations).
 *
 * ### Visual States:
 * 1. **Unauthenticated (`loggedInStatus == false`):** Displays the [UserCreationPanel] header,
 *    input fields with custom pink/green styling, and renders the "Signup" button once input
 *    meets validation rules.
 * 2. **Authenticated (`loggedInStatus == true`):** Replaces the form inputs with a confirmation
 *    card displaying a personalized welcome message for the user.
 *
 * @see UserCreationPanel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCreationForm() {
    var nameValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var loggedInStatus by rememberSaveable { mutableStateOf(false) }

    val fieldColorSchema = TextFieldDefaults.colors(
        unfocusedContainerColor = Pink01,
        focusedContainerColor = Pink03,
        focusedTextColor = Green02,
        unfocusedTextColor = Green03,
        focusedLabelColor = Green02,
        unfocusedLabelColor = Green03,
    );
    val buttonColorScheme = ButtonDefaults.buttonColors(
        containerColor = Green01
    );
    Column (
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        UserCreationPanel()
        if (!loggedInStatus) {
            TextField(

                colors = fieldColorSchema,
                value = nameValue,
                onValueChange = { nameValue = it },
                textStyle = TextStyle(textAlign = TextAlign.Center),
                label = { Text(text = "Please enter your username (Must be non-empty)") }
            )
            Spacer(modifier = Modifier.height(20.dp).width(20.dp))
            TextField(
                colors = fieldColorSchema,
                value = passwordValue,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { passwordValue = it },
                textStyle = TextStyle(textAlign = TextAlign.Center),
                label = { Text(text = "Please enter your password (Must be at least 8 characters long)") }
            )
            Spacer(modifier = Modifier.height(20.dp).width(20.dp))

            if (!nameValue.isEmpty() && !passwordValue.isEmpty()
                && passwordValue.length >= 8
            ) {
                Button(onClick = { loggedInStatus = true }, colors = buttonColorScheme) {
                    Text("Signup")
                }
            }
        } else {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Green04,
                    contentColor = Green02
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)){
                    Text(
                        text = "Welcome to clicker adventure, $nameValue!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}