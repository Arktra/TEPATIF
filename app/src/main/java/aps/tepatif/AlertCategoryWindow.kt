package aps.tepatif

import android.R.attr.category
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertCategoryWindow(
    navController: NavController,
    showDialog: Boolean,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    var category by remember { mutableStateOf("") }

    AlertDialog(
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss,

        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), // Adjust padding to reduce space
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    placeholder = { Text("Category Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Color(0xFF006FFD), RoundedCornerShape(12.dp)),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Menonaktifkan pindah baris dengan tombol enter
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color(0xFF006FFD),
                        unfocusedLabelColor = Color.Gray,
                        focusedBorderColor = Color(0xFF006FFD)
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
            }
        },

        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .border(1.5.dp, Color(0xFF006FFD), RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF006FFD)
                    ),
                    onClick = {
                        onCancel()
                        onDismiss()
                    }
                ) {
                    Text("No")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .border(1.5.dp, Color(0xFF006FFD), RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006FFD),
                        contentColor = Color.White
                    ),
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text("Yes")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AlertCategoryWindowPreview() {
    val navController = rememberNavController()
    AlertCategoryWindow(
        navController = navController,
        showDialog = true,
        title = "Judul",
        onDismiss = { /*TODO*/ },
        onConfirm = { /*TODO*/ },
        onCancel = { /*TODO*/ }
    )
}
