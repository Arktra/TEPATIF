package aps.tepatif

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ReminderOptionsWindow(
    navController: NavController,
    onDismiss: () -> Unit, // Callback for closing the dialog
    onConfirm: (String) -> Unit // Callback for passing the selected option back
) {
    // State for storing the current selection
    val selectedOption = remember { mutableStateOf<String?>(null) }

    // List of reminder options
    val options = listOf(
        "None",
        "5 minutes before",
        "10 minutes before",
        "30 minutes before",
        "1 hour before",
        "1 day before",
        "Custom.."
    )

    // Handle when a selection is made
    val onOptionSelected: (String) -> Unit = { option ->
        selectedOption.value = option // Save the selected option
        onConfirm(option) // Notify the selection to the parent
        onDismiss() // Close the dialog immediately after selection
    }

    // Dialog Box UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // Background overlay
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(246.dp)
                .height(330.dp), // Adjust height based on options
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly // Evenly distribute space between options
            ) {
                // Display each option
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onOptionSelected(option) }, // Handle option click
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Indicator for selected option
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    if (selectedOption.value == option) Color(0xFF71727A) else Color(0xFFC5C6CC),
                                    RoundedCornerShape(10.dp)
                                )
                        )

                        // Option text
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 16.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF71727A)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderOptionsWindowPreview() {
    val navController = rememberNavController()
    ReminderOptionsWindow(
        navController = navController,
        onDismiss = { /* Handle dismiss action */ },
        onConfirm = { selectedOption -> /* Handle selected option */ }
    )
}