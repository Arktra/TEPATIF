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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun OTPValidationScreen(navController: NavController) {
    var otp by remember { mutableStateOf(listOf("", "", "", "")) }

    val constraints = ConstraintSet {
        val otp = createRefFor("otp")
        val button = createRefFor("button")

        constrain(otp) {
            centerVerticallyTo(parent)
//            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(button) {
            bottom.linkTo(parent.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .layoutId("otp"),
//                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter confirmation code",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.layoutId("detailTextBold").padding(bottom = 8.dp)
            )

            Text(
                text = "A 4-digit code was sent to \n name@gmail.com",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.layoutId("detailText").padding(bottom = 24.dp)
            )

            OtpTextField(
                otpText = otp,
                onOtpTextChange = { otp = it },
                otpCount = 4,
                modifier = Modifier.fillMaxWidth().layoutId("otpField")
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .layoutId("button")
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { /* Resend Code */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            ) {
                Text(
                    text = "Resend Code",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF006FFD)
                    )
                )
            }

            Button(
                onClick = {
                    val otpValue = otp.joinToString("")
                    navController.navigate("home_screen")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006FFD)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Continue",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
            }
        }
    }
}


// Komponen OtpTextField
@OptIn(ExperimentalMaterial3Api::class)
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
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Menambahkan jarak antar kolom
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
                    .size(48.dp)
                    .focusRequester(focusRequester), // Attach focusRequester
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFC5C6CC), // Warna border saat tidak fokus
                    focusedBorderColor = Color(0xFF006FFD), // Warna border saat fokus
                ),
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