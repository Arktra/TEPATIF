import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import aps.tepatif.ConfirmWindow
import aps.tepatif.R
import aps.tepatif.backend.BackEndAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, backEndAuth: BackEndAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var showConfirm = remember { mutableStateOf(false) }
    var id = 0
    val focusManager = LocalFocusManager.current

    val constraints = ConstraintSet {
        val logo = createRefFor("logo")
        val welcomeText = createRefFor("welcomeText")
        val emailField = createRefFor("emailField")
        val passwordField = createRefFor("passwordField")
        val forgotPassword = createRefFor("forgotPassword")
        val loginButton = createRefFor("loginButton")
        val errorText = createRefFor("errorText")
        val registerRow = createRefFor("registerRow")
        val divider = createRefFor("divider")
        val socialLoginText = createRefFor("socialLoginText")
        val socialLoginIcons = createRefFor("socialLoginIcons")

        constrain(logo) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(welcomeText) {
            top.linkTo(logo.bottom, margin = 30.dp)
            start.linkTo(parent.start)
        }

        constrain(emailField) {
            top.linkTo(welcomeText.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(passwordField) {
            top.linkTo(emailField.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(forgotPassword) {
            top.linkTo(passwordField.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(loginButton) {
            top.linkTo(forgotPassword.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(errorText) {
            top.linkTo(loginButton.bottom, margin = 8.dp)
            start.linkTo(parent.start)
        }

        constrain(registerRow) {
            top.linkTo(errorText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(divider) {
            top.linkTo(registerRow.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(socialLoginText) {
            top.linkTo(divider.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(socialLoginIcons) {
            top.linkTo(socialLoginText.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Menyebar penuh dari kiri ke kanan
                .fillMaxHeight(1f/3.5f)// Menentukan tinggi background sesuai ukuran logo
                .layoutId("logo")
                .background(Color(0xFFEAF2FF), shape = RoundedCornerShape(12.dp)) // Background untuk logo
                .padding(bottom = 16.dp) // Menambahkan padding di bawah background
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.Center).size(212.dp) // Memposisikan logo di tengah Box
            )
        }

        Text(
            text = "Welcome!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("welcomeText").padding(start = 16.dp, end = 16.dp),
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.layoutId("emailField").fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next // Menonaktifkan pindah baris dengan tombol enter
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down) // Menghapus fokus saat tombol ceklis ditekan
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray, // Warna border saat tidak fokus
                unfocusedLabelColor = Color.Gray, // Warna label saat tidak fokus
                focusedBorderColor = Color(0xFF006FFD)
            ),
            shape = RoundedCornerShape(12.dp),
        )

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
            modifier = Modifier.layoutId("passwordField").fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Menonaktifkan pindah baris dengan tombol enter
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Menghapus fokus saat tombol ceklis ditekan
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray, // Warna border saat tidak fokus
                unfocusedLabelColor = Color.Gray, // Warna label saat tidak fokus
                focusedBorderColor = Color(0xFF006FFD)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Forgot password?",
            style = typography.bodyMedium.copy(
                color = Color(0xFF006FFD),
                fontSize = 12.sp,
                fontWeight = FontWeight.W600,
            ),
            modifier = Modifier.layoutId("forgotPassword").padding(start = 16.dp, end = 16.dp)
        )

        Button(
                onClick = {
//                    if (email.isEmpty() || password.isEmpty()) {
//                        showConfirm.value = true
//                        id = 1
//                    } else {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            val user = backEndAuth.login(email, password)
//                            withContext(Dispatchers.Main) {
//                                if (user != null) {
                                    navController.navigate("home_screen")
//                                } else {
//                                    showConfirm.value = true
//                                    id = 2
//                                }
//                            }
//                        }
//                    }
                },
        modifier = Modifier.layoutId("loginButton").fillMaxWidth().height(48.dp).padding(start = 16.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF006FFD),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
        ) {
        Text("Login")
    }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.layoutId("registerRow").fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                "Not a member?",
                color = Color(0xFF71727A),
                lineHeight = 16.sp,
                fontWeight = FontWeight.W400
            )
            TextButton(onClick = { navController.navigate("sign_up_screen") }) {
                Text(
                    "Register now",
                    color = Color(0xFF006FFD),
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.layoutId("divider").padding(start = 16.dp, end = 16.dp)
        )

        Text(
            text = "Or continue with",
            color = Color(0xFF71727A),
            modifier = Modifier.layoutId("socialLoginText").padding(start = 16.dp, end = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.layoutId("socialLoginIcons").padding(start = 16.dp, end = 16.dp)
        ) {
            IconButton(onClick = { /* Google login */ }, modifier = Modifier.size(40.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /* Apple login */ }, modifier = Modifier.size(40.dp),) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = "Apple",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /* Facebook login */ }, modifier = Modifier.size(40.dp),) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "Facebook",
                    tint = Color.Unspecified
                )
            }
        }

        if (showConfirm.value) {
            if (id == 1) {
                ConfirmWindow(
                    navController = navController,
                    showDialog = true,
                    title = "Error",
                    content = "Mohon isi semua data yang diperlukan",
                    confirmButtonText = "OK",
                    onDismiss = { showConfirm.value = false },
                    onConfirm = { showConfirm.value = false },
                )
            } else {
                ConfirmWindow(
                    navController = navController,
                    showDialog = true,
                    title = "Error",
                    content = "Email atau password salah\nMohon cek kembali",
                    confirmButtonText = "OK",
                    onDismiss = { showConfirm.value = false },
                    onConfirm = { showConfirm.value = false },
                )
            }
        }
    }
}