import aps.tepatif.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Placeholder for image
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 250.dp) // Ukuran tetap: 360px x 300px
                .padding(top = 40.dp) // Padding atas 40px
                .background(Color.LightGray.copy(alpha = 1f)) // Warna LightGray dengan opacity 0 (transparan)
                .align(Alignment.CenterHorizontally), // Posisikan di tengah secara horizontal
        ) {
            Text(
                text = "Logo",
                modifier = Modifier.align(Alignment.Center),
            )
        }


        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Welcome!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 29.05.sp,
            letterSpacing = 0.01.em,
            textAlign = TextAlign.Start,
            textDecoration = TextDecoration.None
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Forgot password?",
                style = typography.bodyMedium.copy(
                    fontFamily = FontFamily.Default, // Replace with your Inter font family
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 14.52.sp,
                    textAlign = TextAlign.Left,
                    color = Color(0xFF006FFD) // Set text color to #006FFD
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton (
            onClick = { navController.navigate("home_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .width(327.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(topStart = 12.dp))
                .alpha(1f), // Set alpha to 1f (fully visible)
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006FFD), // Set background color to #006FFD
                contentColor = Color.White // Keep content color as white
            )
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Align items vertically
        ) {
            Text(
                "Not a member?",
                color = Color(0xFF71727A),
                modifier = Modifier.alignByBaseline() // Align by baseline
            )
            TextButton(
                onClick = { navController.navigate("sign_up_screen") },
                modifier = Modifier.alignByBaseline() // Align by baseline
            ) {
                Text("Register now")
            }
        }

        Divider(
            color = Color.LightGray, // Set the color of the divider
            thickness = 1.dp, // Set the thickness of the divider
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding if needed
        )

        Text(
            text = "Or continue with",
            textAlign = TextAlign.Center, // Mengatur teks agar rata tengah
            color = Color(0xFF71727A),
            modifier = Modifier
                .fillMaxWidth() // Memastikan teks mengambil seluruh lebar layar
                .padding(top = 16.dp, bottom = 8.dp) // Memberikan jarak vertikal untuk estetika
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Using Icons.Filled for social media login buttons
            IconButton(onClick = { /* Google login */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Email Icon",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { /* Apple login */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = "Email Icon",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { /* Apple login */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "Facebook",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}
