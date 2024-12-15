import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import aps.tepatif.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EventDetail(navController: NavController) {
    var selected = remember { mutableStateOf(false) }
    var eventName by remember { mutableStateOf("") }

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

    val focusManager = LocalFocusManager.current

    val constraints = ConstraintSet {
        val navBar = createRefFor("navBar")

        constrain(navBar) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
    }

    ConstraintLayout (constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
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
                        text = "Your Event",
                        modifier = Modifier.padding(bottom = 32.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 29.05.sp,
                        letterSpacing = 0.01.em,
                        textAlign = TextAlign.Start,
                        textDecoration = TextDecoration.None
                    )

                    Text(
                        text = "Today",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    Text(
                        text = "Tommorow",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    Text(
                        text = "15 Des 2024",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )

                    EventCard(
                        eventName = "Nama Event",
                        eventTime = "10:00 - 12:00"
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth() // Lebar penuh
                .height(96.dp)
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                .layoutId("navBar"),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_event), // Ganti dengan nama file SVG Anda
                contentDescription = "Profile", modifier = Modifier
                    .size(96.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Menghilangkan animasi warna abu-abu
                        onClick = {
                            focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                        })
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_schedule), // Ganti dengan nama file SVG Anda
                contentDescription = "Profile", modifier = Modifier
                    .size(96.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Menghilangkan animasi warna abu-abu
                        onClick = {
                            navController.navigate("home_screen")
                            focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                        })
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_settings), // Ganti dengan nama file SVG Anda
                contentDescription = "Profile", modifier = Modifier
                    .size(96.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Menghilangkan animasi warna abu-abu
                        onClick = {
                            navController.navigate("settings_screen")
                            focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                        })
            )
        }
    }
}

@Composable
fun EventCard(
    eventName: String,
    eventTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Lebar penuh
            .clip(RoundedCornerShape(16.dp))
            .padding(bottom = 4.dp)
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
                    text = "Nama Event",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "10:00 - 12:00",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF71727A),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    val navController = rememberNavController()
    EventDetail(navController = navController)
}
