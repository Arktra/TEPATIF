import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sign Up",
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
            modifier = Modifier
                .fillMaxWidth() // Memastikan teks mengambil seluruh lebar layar
                .padding(bottom = 8.dp) // Memberikan jarak vertikal untuk estetika
        )

        Text(
            text = "Name",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth() // Memastikan teks mengambil seluruh lebar layar
                .padding(top = 8.dp) // Memberikan jarak vertikal untuk estetika
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("your_name") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Email Address",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth() // Memastikan teks mengambil seluruh lebar layar
                .padding(top = 16.dp) // Memberikan jarak vertikal untuk estetika
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("name@email.com") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "Password",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth() // Memastikan teks mengambil seluruh lebar layar
                .padding(top = 16.dp) // Memberikan jarak vertikal untuk estetika
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
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Confirm Password") },
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

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp) // Menentukan ukuran checkbox 24x24px
                    .border(
                        width = 1.5.dp, // Border 1.5px
                        color = Color(0xFFC5C6CC), // Warna border sesuai yang diminta (#C5C6CC)
                        shape = RoundedCornerShape(6.dp) // Border radius 6px pada bagian kiri atas
                    )
                    .clickable { isChecked = !isChecked } // Menangani aksi klik untuk checkbox
                    .padding(2.dp) // Memberikan sedikit ruang di dalam kotak untuk memperbaiki tampilan
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check, // Gambar ceklis
                        contentDescription = "Checked",
                        tint = Color(0xFF006FFD), // Mengatur warna ceklis
                        modifier = Modifier.align(Alignment.Center) // Menyelaraskan ikon di tengah
                    )
                }
            }

            // AnnotatedString untuk membuat teks dengan link
            val annotatedString = buildAnnotatedString {
                append("I've read and agree with the ")
                pushStringAnnotation(tag = "terms", annotation = "https://example.com/terms")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("terms and conditions")
                }
                pop()
                append(" and the ")
                pushStringAnnotation(
                    tag = "privacy",
                    annotation = "https://example.com/privacy"
                )
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("privacy policy")
                }
                pop()
            }

            // Teks dengan link yang bisa diklik
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let {
                        val url = it.item
                        // Tanggapi URL sesuai dengan tujuan, misalnya menggunakan Intent untuk membuka browser
                        println("Open URL: $url")
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            )
        }



        Column(
                modifier = Modifier
                    .fillMaxSize() // Mengisi seluruh ruang layar
                    .padding(16.dp), // Menambahkan padding
                verticalArrangement = Arrangement.Bottom, // Menempatkan elemen di bagian bawah
                horizontalAlignment = Alignment.CenterHorizontally // Menyusun elemen secara horizontal di tengah
            ) {
                // Konten lainnya di sini (misalnya, teks, input, dll.)

                Button(
                    onClick = { navController.navigate("otp_validation") },
                    modifier = Modifier
                        .fillMaxWidth() // Lebar penuh
                        .width(327.dp) // Lebar tetap 327dp
                        .height(48.dp) // Tinggi tombol 48dp
                        .clip(RoundedCornerShape(topStart = 12.dp)) // Membuat sudut atas tombol melengkung
                        .alpha(1f), // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006FFD), // Warna latar belakang tombol
                        contentColor = Color.White // Warna konten tombol (teks)
                    )
                ) {
                    Text("Sign Up") // Teks dalam tombol
                }
            }
        }

    }


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(navController = navController)
}
