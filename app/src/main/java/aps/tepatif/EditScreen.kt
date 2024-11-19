package aps.tepatif

import CustomCard
import NewEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import aps.tepatif.ConfirmWindow
import aps.tepatif.R
import aps.tepatif.ScheduleCart
import java.util.Calendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@Composable
fun EditScreen(navController: NavController) {
    var selected = remember { mutableStateOf(false) }
    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var startDate by remember { mutableStateOf("") }  // Track start date
    var endDate by remember { mutableStateOf("") }    // Track end date
    var isStartSelected by remember { mutableStateOf(false) }  // Track whether Start or End is selected

    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentYear = calendar.get(Calendar.YEAR)

    // Format tanggal menjadi "Jun 10, 2024"
    val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    val formattedDate = dateFormat.format(currentTime)

    // Format waktu dengan AM/PM
    val timeFormat = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    var formattedTime = timeFormat.format(currentTime)

    // Hapus nol di depan jika ada
    if (formattedTime.startsWith("0")) {
        formattedTime = formattedTime.substring(1)
    }

    var categories =
        remember { mutableStateListOf("Design", "Work", "Personal") } // Daftar kategori
    var newCategoryName by remember { mutableStateOf("") } // Untuk nama kategori baru
    var showCategoryDialog by remember { mutableStateOf(false) } // Untuk kontrol dialog

    val showOptions = remember { mutableStateOf(false) }
    val selectedReminder = remember { mutableStateOf("None") } // Default reminder is "None"

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Edit Event",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 29.05.sp,
                letterSpacing = 0.01.em,
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.None
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Event Name",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Tell us everything.", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(94.dp),
                maxLines = 1,
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "Start",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        isStartSelected = true
                        selected.value = true
                    },
                    modifier = Modifier
                        .height(34.dp)
                        .width(123.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEF)),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    Text(
                        text = if (startDate.isNotEmpty()) startDate else formattedDate,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { /* aksi yang ingin dijalankan saat button ditekan */ },
                    modifier = Modifier
                        .height(34.dp)
                        .width(86.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEF)),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "End",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
//                        isStartSelected = false
                        selected.value = true
                    },
                    modifier = Modifier
                        .height(34.dp)
                        .width(123.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEF)),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    Text(
                        text = if (endDate.isNotEmpty()) endDate else formattedDate,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { /* aksi yang ingin dijalankan saat button ditekan */ },
                    modifier = Modifier
                        .height(34.dp)
                        .width(86.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEF)),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Menambah jarak 16dp

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .wrapContentHeight() // Tinggi menyesuaikan konten
                    .clip(RoundedCornerShape(topStart = 16.dp)) // Membuat sudut atas melengkung
                    .alpha(1f) // Alpha sepenuhnya terlihat
                    .clickable { /* Action saat Card ditekan */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FE) // Warna latar belakang Card
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp) // Padding untuk Card
                ) {
                    // Header atau Judul
                    Text(
                        text = "Category",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Grid dengan elemen di dalamnya
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3), // Grid dengan 2 kolom
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp), // Padding dalam grid
                        horizontalArrangement = Arrangement.spacedBy(8.dp), // Jarak horizontal antar item
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak vertikal antar item
                    ) {
                        items(3) { // Mengulang 3 Card
                            CustomCard() // Memanggil fungsi Card
                        }
                    }

                    Button(
                        onClick = {
                            showCategoryDialog = true
                        }, // Menampilkan dialog untuk menambah kategori
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(bottom = 16.dp), // Memberikan jarak antara tombol dan grid
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006FFD)) // Ganti warna tombol sesuai keinginan
                    ) {
                        Text(text = "Add Category", color = Color.White)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Full width
                    .height(72.dp) // Card height
                    .clip(RoundedCornerShape(topStart = 16.dp))
                    .alpha(1f)
                    .clickable { showOptions.value = true }, // Show options when clicked
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FE)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Left content with reminder label and value
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_reminder), // Reminder Icon
                                contentDescription = "Reminder Icon",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 8.dp)
                            )

                            Column {
                                Text(
                                    text = "Reminder",
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = selectedReminder.value, // Display the selected reminder
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF71727A)
                                )
                            }
                        }
                    }

                    // Spacer untuk memberikan jarak di antara elemen kiri dan tombol kanan
                    Spacer(modifier = Modifier.width(16.dp)) // Menambah jarak 16dp

                    // Tombol Add di kanan
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color(0xFF007BFF), RoundedCornerShape(12.dp))
                            .width(66.dp)
                            .height(40.dp)
                            .clickable { showOptions.value = true } // Show dialog on click
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Add",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006FFD)
                            )
                        }
                    }


                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize() // Ensures the Box takes the full available size
            ) {

                Button(
                    onClick = { navController.navigate("home_screen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.BottomCenter), // Use BottomCenter inside Box
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006FFD),
                        contentColor = Color.White
                    )
                ) {
                    Text("Save")
                }
            }
        }
        if (selected.value) {
            ScheduleCart(
                navController = navController,
                clicked = { selectedDate ->
                    if (isStartSelected) {
                        startDate = selectedDate  // Update Start Date
                        isStartSelected = false
                    } else {
                        endDate = selectedDate  // Update End Date
                    }
                    selected.value = false // Close the calendar
                }
            )

        }

        if (showCategoryDialog) {
            AddCategoryDialog(
                newCategoryName = newCategoryName,
                onNewCategoryChange = { newCategoryName = it },
                onSave = {
                    if (newCategoryName.isNotBlank()) {
                        categories.add(newCategoryName)
                        newCategoryName = ""
                        showCategoryDialog = false
                    }
                },
                onCancel = { showCategoryDialog = false }
            )
        }
        if (showOptions.value) {
            ReminderOptions(
                navController = navController,
                onDismiss = { showOptions.value = false }, // Dismiss the dialog
                onConfirm = {
                    selectedReminder.value = it
                } // Update selected reminder on confirm
            )
        }
    }
}

@Composable
fun AddCategoryDialog(
    newCategoryName: String,
    onNewCategoryChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Add New Category") },
        text = {
            OutlinedTextField(
                value = newCategoryName,
                onValueChange = onNewCategoryChange,
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CustomCard() {
    Card(
        modifier = Modifier
            .width(95.dp) // Lebar tetap
            .height(45.dp) // Tinggi Card
            .clip(RoundedCornerShape(topStart = 16.dp)) // Membuat sudut atas melengkung
            .alpha(1f) // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
            .clickable { /* Action saat Card ditekan */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6EFFE) // Warna latar belakang Card
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Menempatkan elemen-elemen di kiri
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            // Menampilkan informasi jadwal di kiri
            Column(
                modifier = Modifier
                    .weight(1f) // Menggunakan ruang yang tersisa untuk teks
                    .size(95.dp, 44.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center, // Menyusun elemen secara vertikal di tengah
                horizontalAlignment = Alignment.Start // Menyusun teks di kiri
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    // Menambahkan gambar di kiri
                    Image(
                        painter = painterResource(id = R.drawable.ic_design_oval), // Ganti dengan id gambar Anda
                        contentDescription = "Design Icon",
                        modifier = Modifier
                            .size(16.dp) // Ukuran gambar
                            .padding(start = 2.dp, end = 2.dp) // Jarak antara gambar dan teks
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Teks pada bagian kiri
                    Column {
                        Text(
                            text = "Design",
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    val navController = rememberNavController()
    EditScreen(navController = navController)
}
