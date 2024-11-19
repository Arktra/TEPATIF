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
fun EventDetail(navController: NavController) {
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
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nama Event",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 29.05.sp,
                letterSpacing = 0.01.em,
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.None
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(300.dp) // Lebar tetap 300dp
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

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(300.dp) // Lebar tetap 300dp
                    .height(101.dp) // Tinggi Card
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

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(300.dp) // Lebar tetap 300dp
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
                        columns = GridCells.Fixed(2), // Grid dengan 2 kolom
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp), // Padding dalam grid
                        horizontalArrangement = Arrangement.spacedBy(8.dp), // Jarak horizontal antar item
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak vertikal antar item
                    ) {
                        items(3) { // Mengulang 3 Card
                            CustomCard() // Memanggil fungsi Card
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
                        .fillMaxSize()
                        .padding(16.dp), // Optional: adds padding around the column
                    verticalArrangement = Arrangement.Bottom, // Align buttons to the bottom of the Box
                    horizontalAlignment = Alignment.CenterHorizontally // Align buttons horizontally in the center
                ) {
                    // Hapus button
                    Button(
                        onClick = { navController.navigate("edit_screen") },
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
                        onClick = {  },
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
                            "Hapus",
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
            Spacer(modifier = Modifier.height(12.dp))
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
fun EventDetail() {
    val navController = rememberNavController()
    EventDetail(navController = navController)
}
