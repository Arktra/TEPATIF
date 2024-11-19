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

@Composable
fun NewEvent(navController: NavController) {
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
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Add New Event",
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
                        .width(123.dp)
                        .clip(RoundedCornerShape(6.dp)),
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
                        .width(86.dp)
                        .clip(RoundedCornerShape(6.dp)),
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
                        .width(123.dp)
                        .clip(RoundedCornerShape(6.dp)),
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
                        .width(86.dp)
                        .clip(RoundedCornerShape(6.dp)),
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
                    .width(327.dp) // Lebar tetap 327dp
                    .height(72.dp) // Tinggi Card
                    .clip(RoundedCornerShape(topStart = 16.dp)) // Membuat sudut atas Card melengkung
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
                                painter = painterResource(id = R.drawable.ic_reminder), // Ganti dengan id gambar Anda
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

                    // Spacer untuk memberikan jarak di antara elemen kiri dan tombol kanan
                    Spacer(modifier = Modifier.width(16.dp)) // Menambah jarak 16dp

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
                            .clickable { /* Action untuk detail kanan */ }
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
    }
}


@Preview(showBackground = true)
@Composable
fun NewEventScreen() {
    val navController = rememberNavController()
    NewEvent(navController = navController)
}
