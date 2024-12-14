import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import aps.tepatif.backend.BackEndAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, backEndAuth: BackEndAuth) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    val constraints = ConstraintSet {
        val signUpText = createRefFor("signUpText")
        val createText = createRefFor("createText")
        val nameText = createRefFor("nameText")
        val nameField = createRefFor("nameField")
        val emailText = createRefFor("emailText")
        val emailField = createRefFor("emailField")
        val passwordText = createRefFor("passwordText")
        val passwordField = createRefFor("passwordField")
        val confirmPasswordField = createRefFor("confirmPasswordField")
        val checkedRow = createRefFor("checkedRow")
        val signUpButton = createRefFor("signUpButton")

        constrain(signUpText) {
            top.linkTo(parent.top, margin = 30.dp)
            start.linkTo(parent.start)
        }

        constrain(createText) {
            top.linkTo(signUpText.bottom)
            start.linkTo(parent.start)
        }

        constrain(nameText) {
            top.linkTo(createText.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(nameField) {
            top.linkTo(nameText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(emailText) {
            top.linkTo(nameField.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(emailField) {
            top.linkTo(emailText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(passwordText) {
            top.linkTo(emailField.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(passwordField) {
            top.linkTo(passwordText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(confirmPasswordField) {
            top.linkTo(passwordField.bottom, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(checkedRow) {
            top.linkTo(confirmPasswordField.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(signUpButton) {
            bottom.linkTo(parent.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Sign Up",
            modifier = Modifier.layoutId("signUpText").padding(start = 16.dp, end = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 29.05.sp,
            letterSpacing = 0.01.em,
            textAlign = TextAlign.Start,
            textDecoration = TextDecoration.None
        )

        Text(
            text = "Create an account to get started",
            color = Color(0xFF71727A),
            modifier = Modifier.layoutId("createText").padding(start = 16.dp, end = 16.dp)
        )

        Text(
            text = "Name",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("nameText").padding(start = 16.dp, end = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().layoutId("nameField").padding(start = 16.dp, end = 16.dp),
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
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Email Address",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().layoutId("emailText").padding(start = 16.dp, end = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("emailField")
                .padding(start = 16.dp, end = 16.dp),
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

        Text(
            text = "Password",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("passwordText").padding(start = 16.dp, end = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Create a password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().layoutId("passwordField").padding(start = 16.dp, end = 16.dp),
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
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().layoutId("confirmPasswordField").padding(start = 16.dp, end = 16.dp),
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.layoutId("checkedRow").padding(start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 1.5.dp,
                        color = if (isChecked) Color(0xFF006FFD) else Color(0xFFC5C6CC),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { isChecked = !isChecked },
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Checked",
                        tint = Color(0xFF006FFD),
                    )
                }
            }

            val annotatedString = buildAnnotatedString {
                append("I've read and agree with the ")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("terms and conditions")
                }
                append(" and the ")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("privacy policy")
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let {
                        /* URL */
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier.layoutId("signUpButton").padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val user = backEndAuth.register(email, password)
                            if (user != null) {
                                withContext(Dispatchers.Main) {
                                    navController.navigate("otp_validation")
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006FFD),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Sign Up", modifier = Modifier.padding(8.dp))
            }

            registerError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}