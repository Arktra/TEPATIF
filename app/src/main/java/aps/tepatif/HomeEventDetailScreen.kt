import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeEventDetail(navController: NavController) {
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
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(currentTime)

    // Format waktu dengan AM/PM
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    var formattedTime = timeFormat.format(currentTime)

    // Hapus nol di depan jika ada
    if (formattedTime.startsWith("0")) {
        formattedTime = formattedTime.substring(1)
    }

    var showDeleteConfirm = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nama Event",
                    modifier = Modifier.padding(bottom = 32.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 29.05.sp,
                    letterSpacing = 0.01.em,
                    textAlign = TextAlign.Start,
                    textDecoration = TextDecoration.None
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth() // Lebar penuh
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .height(72.dp) // Tinggi Card
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
                            Text(
                                text = "10.00-12.00",
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Minggu, 24 November 2024",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF71727A),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth() // Lebar penuh
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .height(101.dp) // Tinggi Card
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
                            Text(
                                text = "Nama Event",
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Description",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF71727A),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = "Ini merupakan contoh deskripsi dari aplikasi ini ",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF71727A),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth() // Lebar penuh
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .height(72.dp) // Tinggi Card
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
                                    painter = painterResource(id = R.drawable.ic_reminder_grey), // Ganti dengan id gambar Anda
                                    contentDescription = "Reminder Icon",
                                    modifier = Modifier
                                        .size(48.dp) // Ukuran gambar
                                        .padding(end = 8.dp) // Jarak antara gambar dan teks
                                )

                                // Teks pada bagian kiri
                                Column {
                                    Text(
                                        text = "X before",
                                        fontSize = 14.sp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth() // Lebar penuh
                        .padding(bottom = 16.dp)
                        .wrapContentHeight() // Tinggi menyesuaikan konten
                        .clip(RoundedCornerShape(16.dp))
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
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak vertikal antar item
                        ) {
                            val cards =
                                listOf("Bruh", "Bruh", "Bruh", "Bruh") // Example list of cards
                            cards.chunked(2).forEach { rowCards ->
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
                                    repeat(2 - rowCards.size) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize() // Ensures the Box takes the full available size
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom, // Align buttons to the bottom of the Box
                        horizontalAlignment = Alignment.CenterHorizontally // Align buttons horizontally in the center
                    ) {
                        // Hapus button
                        Button(
                            onClick = { navController.navigate("edit_event_screen") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp), // Ensure consistent height for all buttons
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFB37C),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                "Edit",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) // Ensure text is centered
                        }

                        // Add Spacer to maintain consistent distance between buttons
                        Spacer(modifier = Modifier.height(8.dp)) // Adds space between buttons

                        // Edit button
                        Button(
                            onClick = { showDeleteConfirm.value = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp), // Ensure all buttons have the same height
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF616D),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                "Delete",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) // Ensure text is centered
                        }

                        // Add Spacer again to maintain space between buttons
                        Spacer(modifier = Modifier.height(8.dp)) // Adds space between buttons

                        // Back button
                        Button(
                            onClick = { navController.navigate("home_screen") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp), // Ensure consistent height for all buttons
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF006FFD),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                "Back",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) // Ensure text is centered
                        }
                    }
                }

            }
        }
    }

    if (showDeleteConfirm.value) {
        ConfirmWindow(
            navController = navController,
            showDialog = showDeleteConfirm.value,
            title = "Are you sure to\nDelete this Event?",
            onDismiss = { showDeleteConfirm.value = false },
            onConfirm = {
                showDeleteConfirm.value = false
                navController.navigate("home_screen")
            },
            onCancel = { showDeleteConfirm.value = false }
        )
    }

}

@Composable
fun CustomCard(text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(45.dp)
            .clip(RoundedCornerShape(12.dp))
            .alpha(1f)
            .clickable { /* Action saat Card ditekan */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE6EFFE)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_design_oval),
                        contentDescription = "Design Icon",
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 2.dp, end = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = text,
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
fun HomeEventDetailPreview() {
    val navController = rememberNavController()
    HomeEventDetail(navController = navController)
}
