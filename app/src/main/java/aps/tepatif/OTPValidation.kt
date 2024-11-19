package aps.tepatif

import LoginScreen
import SignUpScreen
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun OTPValidationScreen(navController: NavController) {
    var otp by remember { mutableStateOf(listOf("", "", "", "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Keep other content at the top
    ) {
        Spacer(modifier = Modifier.height(300.dp))
        // Judul OTP
        Text(
            text = "Enter confirmation code",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Deskripsi OTP
        Text(
            text = "A 4-digit code was sent to name@gmail.com",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Kotak Input OTP
        OtpTextField(
            otpText = otp,
            onOtpTextChange = { otp = it },
            otpCount = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f)) // This will push the buttons to the bottom

        // Tombol Resend Code
        TextButton(
            onClick = { /* Logic to resend OTP */ },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                text = "Resend Code",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Blue
                )
            )
        }

        // Tombol Continue
        Button(
            onClick = {
                val otpValue = otp.joinToString("")
                println("Entered OTP: $otpValue")
                navController.navigate("next_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006FFD))
        ) {
            Text(
                text = "Continue",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
        }
    }
}


// Komponen OtpTextField
@Composable
fun OtpTextField(
    otpText: List<String>,
    onOtpTextChange: (List<String>) -> Unit,
    otpCount: Int,
    modifier: Modifier = Modifier
) {
    // List of FocusRequester for each OTP input field
    val focusRequesters = List(otpCount) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Menambahkan jarak antar kolom
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center) // Membungkus konten dan memusatkannya
    ) {
        repeat(otpCount) { index ->
            val focusRequester = focusRequesters[index]

            OutlinedTextField(
                value = otpText.getOrNull(index) ?: "",
                onValueChange = { value ->
                    val newOtpText = otpText.toMutableList()
                    newOtpText[index] = value.take(1) // Allow only one character

                    // Update OTP values
                    onOtpTextChange(newOtpText)

                    // Automatically move focus to the next box when a value is entered
                    if (value.isNotEmpty() && index < otpCount - 1) {
                        focusRequesters[index + 1].requestFocus()
                    }

                    // Automatically move focus to the previous box when a value is deleted
                    if (value.isEmpty() && index > 0) {
                        focusRequesters[index - 1].requestFocus()
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .focusRequester(focusRequester), // Attach focusRequester
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }

    // Initial focus on the first OTP field when the screen is loaded
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}








@Preview(showBackground = true)
@Composable
fun OTPValidationPreview() {
    val navController = rememberNavController()
    OTPValidationScreen(navController = navController)
}