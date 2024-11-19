package aps.tepatif

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ConfirmWindow(
    navController: NavController,
    message: String? = null,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    // Box with a translucent background to simulate modal behavior
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // Adjust the alpha to show transparency
        contentAlignment = Alignment.Center
    ) {
        // Dialog Box with shadow to simulate elevation
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp), // Adjusted height to fit the content
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxHeight()
                    .padding(bottom = 16.dp), // Add padding to ensure space at the bottom
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween // Ensures space is distributed appropriately
            ) {
                // Title Text
                Text(
                    text = "Confirmation",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Message Text
                Text(
                    text = message ?: "Are you sure?", // Default message if null
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )

                // Buttons Row at the bottom
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Cancel Button with Border
                    onCancel?.let {
                        TextButton(
                            onClick = {
                                onCancel() // Execute the cancellation logic
                            },
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    Color(0xFF006FFD),
                                    RoundedCornerShape(12.dp)
                                ) // Adding border to Cancel button
                                .width(130.dp)
                                .height(40.dp)
                        ) {
                            Text(text = "No", color = Color(0xFF006FFD), fontSize = 12.sp)
                        }
                    }

                    // Confirm Button with Border
                    onConfirm?.let {
                        Box(
                            modifier = Modifier
                                .width(130.dp)
                                .height(40.dp)
                                .border(2.dp, Color(0xFF007BFF), RoundedCornerShape(12.dp)) // Apply border here
                                .background(Color(0xFF006FFD), shape = RoundedCornerShape(12.dp)) // Apply background with same corner shape
                                .clip(RoundedCornerShape(12.dp)) // Ensure clipping for the correct rounded corners
                        ) {
                            TextButton(
                                onClick = {
                                    onConfirm() // Execute the confirmation logic
                                },
                                modifier = Modifier.fillMaxSize(), // Fill the Box size
                            ) {
                                Text(text = "Yes", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ConfirmWindowPreview() {
    val navController = rememberNavController()
    ConfirmWindow(navController = navController,
        message = "Are you sure you want to proceed?",
        onConfirm = {
            // Action on confirmation
            false // Hide the dialog after confirmation
        },
        onCancel = {
            // Action on cancellation
            false // Hide the dialog after cancellation
        })
}