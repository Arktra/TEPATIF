import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import aps.tepatif.AlertCategoryWindow
import aps.tepatif.ConfirmWindow
import aps.tepatif.R
import aps.tepatif.ReminderOptionsWindow
import aps.tepatif.RepeatOptionsWindow
import aps.tepatif.SelectDateWindow
import aps.tepatif.SelectTimeWindow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEvent(navController: NavController) {
    var selected = remember { mutableStateOf(false) }
    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var startDate by remember { mutableStateOf("") }  // Track start date
    var endDate by remember { mutableStateOf("") }    // Track end date
    var isStartSelected by remember { mutableStateOf(false) }  // Track whether Start or End is selected

    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentYear = calendar.get(Calendar.YEAR)

    // Format tanggal menjadi "Jun 10, 2024"
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(currentTime)

    // Format waktu dengan AM/PM
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    var formattedTime = timeFormat.format(currentTime)

    // Hapus nol di depan jika ada
    if (formattedTime.startsWith("0")) {
        formattedTime = formattedTime.substring(1)
    }

    var categories =
        remember { mutableStateListOf("Design", "Work", "Personal") } // Daftar kategori
    var newCategoryName by remember { mutableStateOf("") } // Untuk nama kategori baru

    var showTimeWindow = remember { mutableStateOf(false) }
    var showAddCategoryDialog = remember { mutableStateOf(false) }
    var showReminderOptions = remember { mutableStateOf(false) }
    var showRepeatOptions = remember { mutableStateOf(false) }
    var showSaveConfirm = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            Text(
                text = "Add New Event",
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 29.05.sp,
                letterSpacing = 0.01.em,
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.None
            )

            Text(
                text = "Event Name",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                placeholder = { Text("Event Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
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
                text = "Description",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Tell us everything.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
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
                text = "Location",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                placeholder = { Text("Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
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
                text = "Start",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
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
                    onClick = {
                        showTimeWindow.value = true
                    },
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

            Text(
                text = "End",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
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
                    onClick = { showTimeWindow.value = true },
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


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header atau Judul
                Text(
                    text = "Category",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                // Grid dengan elemen di dalamnya
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak vertikal antar item
                ) {
                    val cards = listOf("Bruh", "Bruh", "Bruh", "Bruh") // Example list of cards
                    cards.chunked(3).forEach { rowCards ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp) // Jarak horizontal antar item
                        ) {
                            rowCards.forEach { cardText ->
                                CustomCard(
                                    text = cardText,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Add empty spaces if the row has less than 3 cards
                            repeat(3 - rowCards.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        showAddCategoryDialog.value = true
                    }, // Menampilkan dialog untuk menambah kategori
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            top = 4.dp,
                            bottom = 8.dp
                        ), // Memberikan jarak antara tombol dan grid
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006FFD)) // Ganti warna tombol sesuai keinginan
                ) {
                    Text(text = "Add New Category", color = Color.White)
                }
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(327.dp) // Lebar tetap 327dp
                    .padding(bottom = 8.dp)
                    .height(72.dp) // Tinggi Card
                    .clip(RoundedCornerShape(16.dp)) // Membuat sudut atas Card melengkung
                    .alpha(1f) // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
                    .clickable { /* Action saat Card ditekan */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FE) // Warna latar belakang Card
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // Menempatkan elemen-elemen di kiri
                ) {
                    // Menampilkan informasi jadwal di kiri
                    Column(
                        modifier = Modifier
                            .weight(1f) // Menggunakan ruang yang tersisa untuk teks
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center, // Menyusun elemen secara vertikal di tengah
                        horizontalAlignment = Alignment.Start // Menyusun teks di kiri
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Menambahkan gambar di kiri
                            Image(
                                painter = painterResource(id = R.drawable.ic_reminder_blue), // Ganti dengan id gambar Anda
                                contentDescription = "Reminder Icon",
                                modifier = Modifier
                                    .size(48.dp) // Ukuran gambar
                                    .padding(end = 8.dp) // Jarak antara gambar dan teks
                            )

                            // Teks pada bagian kiri
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
                                    text = "None",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF71727A)
                                )
                            }
                        }
                    }

                    // Tombol Detail di kanan
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(12.dp)) // Sudut melengkung pada sisi kanan
                            .border(
                                2.dp,
                                Color(0xFF007BFF),
                                RoundedCornerShape(12.dp)
                            ) // Menambahkan border dan warna pada border
                            .width(66.dp)
                            .height(40.dp)
                            .clickable { showReminderOptions.value = true }
                            .padding(4.dp) // Menambahkan padding sekitar tombol detail
                    ) {
                        // Menampilkan teks pada Card Detail
                        Box(
                            contentAlignment = Alignment.Center, // Memastikan teks berada di tengah
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Add",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007BFF),
                            ) // Teks pada Card Detail
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(327.dp) // Lebar tetap 327dp
                    .height(72.dp) // Tinggi Card
                    .clip(RoundedCornerShape(16.dp)) // Membuat sudut atas Card melengkung
                    .alpha(1f) // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
                    .clickable { /* Action saat Card ditekan */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FE) // Warna latar belakang Card
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // Menempatkan elemen-elemen di kiri
                ) {
                    // Menampilkan informasi jadwal di kiri
                    Column(
                        modifier = Modifier
                            .weight(1f) // Menggunakan ruang yang tersisa untuk teks
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center, // Menyusun elemen secara vertikal di tengah
                        horizontalAlignment = Alignment.Start // Menyusun teks di kiri
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Menambahkan gambar di kiri
                            Image(
                                painter = painterResource(id = R.drawable.ic_repeat), // Ganti dengan id gambar Anda
                                contentDescription = "Reminder Icon",
                                modifier = Modifier
                                    .size(48.dp) // Ukuran gambar
                                    .padding(end = 8.dp) // Jarak antara gambar dan teks
                            )

                            // Teks pada bagian kiri
                            Column {
                                Text(
                                    text = "Repeat",
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "None",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF71727A)
                                )
                            }
                        }
                    }

                    // Tombol Detail di kanan
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(12.dp)) // Sudut melengkung pada sisi kanan
                            .border(
                                2.dp,
                                Color(0xFF007BFF),
                                RoundedCornerShape(12.dp)
                            ) // Menambahkan border dan warna pada border
                            .width(66.dp)
                            .height(40.dp)
                            .clickable { showRepeatOptions.value = true }
                            .padding(4.dp) // Menambahkan padding sekitar tombol detail
                    ) {
                        // Menampilkan teks pada Card Detail
                        Box(
                            contentAlignment = Alignment.Center, // Memastikan teks berada di tengah
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Add",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007BFF),
                            ) // Teks pada Card Detail
                        }
                    }
                }
            }

            Button(
                onClick = { showSaveConfirm.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
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
        SelectDateWindow(
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

    if (showTimeWindow.value) {
        SelectTimeWindow(
            navController = navController,
            clicked = {
                showTimeWindow.value = false
            }
        )
    }

    if (showAddCategoryDialog.value) {
        AlertCategoryWindow(
            navController = navController,
            showDialog = showAddCategoryDialog.value,
            title = "Add New Category",
            onDismiss = { showAddCategoryDialog.value = false },
            onConfirm = { showAddCategoryDialog.value = false },
            onCancel = { showAddCategoryDialog.value = false }
        )
    }

    if (showReminderOptions.value) {
        ReminderOptionsWindow(
            navController = navController,
            onDismiss = { showReminderOptions.value = false },
            onConfirm = { showReminderOptions.value = false }
        )
    }

    if (showRepeatOptions.value) {
        RepeatOptionsWindow(
            navController = navController,
            onDismiss = { showRepeatOptions.value = false },
            onConfirm = { showRepeatOptions.value = false }
        )
    }

    if (showSaveConfirm.value) {
        ConfirmWindow(
            navController = navController,
            showDialog = showSaveConfirm.value,
            title = "Are you sure to\nAdd this Event?",
            onDismiss = { showSaveConfirm.value = false },
            onConfirm = {
                showSaveConfirm.value = false
                navController.navigate("home_screen")
            },
            onCancel = { showSaveConfirm.value = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NewEventScreen() {
    val navController = rememberNavController()
    NewEvent(navController = navController)
}
