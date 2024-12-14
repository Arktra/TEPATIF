package aps.tepatif

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ConfirmWindow(
    navController: NavController,
    showDialog: Boolean,
    title: String,
    content: String,
    confirmButtonText: String = "Yes",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)? = null
) {
    AlertDialog(
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss,

        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = content,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF71727A),
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (onCancel != null) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp) // Set height to 40.dp
                            .border(1.5.dp, Color(0xFF006FFD), RoundedCornerShape(12.dp))
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, // Set container color
                            contentColor = Color(0xFF006FFD) // Set text color
                        ),
                        onClick = {
                            onCancel()
                            onDismiss()
                        }
                    ) {
                        Text("No")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp) // Set height to 40.dp
                        .border(1.5.dp, Color(0xFF006FFD), RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006FFD), // Set container color
                        contentColor = Color.White // Set text color
                    ),
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text(confirmButtonText)
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ConfirmWindowPreview() {
    val navController = rememberNavController()
    ConfirmWindow(
        navController = navController,
        showDialog = true,
        onDismiss = { /*TODO*/ },
        onConfirm = { /*TODO*/ },
//        onCancel = { /*TODO*/ },
        title = "Judul",
        content = "Mohon isi semua data yang diperlukan"
    )

}