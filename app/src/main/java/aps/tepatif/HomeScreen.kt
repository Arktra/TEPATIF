import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import aps.tepatif.AlertCategoryWindow
import aps.tepatif.ConfirmWindow
import aps.tepatif.R
import aps.tepatif.ScheduleCart
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController) {
    val calendar = Calendar.getInstance()
    var showSeachCategory = remember { mutableStateOf(false) }
    val selectedDay = remember { mutableStateOf<Int?>(calendar.get(Calendar.DAY_OF_MONTH)) }
    val currentMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1) }
    val currentYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    val isToday = selectedDay.value == calendar.get(Calendar.DAY_OF_MONTH) &&
            currentMonth.value == (calendar.get(Calendar.MONTH) + 1) &&
            currentYear.value == calendar.get(Calendar.YEAR)

    val schedules = listOf(
        ScheduleItem("APS", "08:40 - 10:20"),
        ScheduleItem("RPL", "10:20 - 12:00"),
        ScheduleItem("Praktikum APS", "09:50 - 10:20"),
        ScheduleItem("Praktikum Jarkom", "10:20 - 11:50"),
        ScheduleItem("Praktikum Basis Data", "13:50 - 14:20"),
    )

    val todaySchedules = if (isToday) {
        // Menampilkan satu jadwal jika hari ini dipilih
        listOf(schedules.first())
    } else {
        emptyList()
    }

    val novemberFirstSchedules = if (selectedDay.value == 1 && currentMonth.value == 11) {
        // Menambahkan semua jadwal pada 1 November
        schedules
    } else {
        emptyList()
    }

    val allSchedules = todaySchedules + novemberFirstSchedules

    val focusManager = LocalFocusManager.current

    val constraints = ConstraintSet {
        val timeSpan = createRefFor("timeSpan")
        val days = createRefFor("days")
        val dates = createRefFor("dates")
        val filterButton = createRefFor("filterButton")
        val lazyColumn = createRefFor("lazyColumn")
        val card = createRefFor("card")
        val addEventButton = createRefFor("addEventButton")
        val navBar = createRefFor("navBar")

        constrain(timeSpan) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.percent(0.7f)
            height = Dimension.percent(0.02f)
        }

        constrain(days) {
            top.linkTo(timeSpan.bottom)
            start.linkTo(parent.start)
            width = Dimension.percent(0.35f)
            height = Dimension.percent(0.03f)
        }

        constrain(dates) {
            top.linkTo(days.bottom)
            start.linkTo(parent.start)
            width = Dimension.percent(0.35f)
            height = Dimension.percent(0.19f)
        }

        constrain(filterButton) {
            top.linkTo(dates.bottom)
            start.linkTo(parent.start)
        }

        constrain(lazyColumn) {
            top.linkTo(filterButton.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(card) {
            top.linkTo(lazyColumn.bottom)
            start.linkTo(parent.start)
        }

        constrain(addEventButton) {
            top.linkTo(card.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(navBar) {
            top.linkTo(addEventButton.bottom)
            start.linkTo(parent.start)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.layoutId("timeSpan"),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol bulan sebelumnya
                IconButton(
                    onClick = {
                        if (currentMonth.value == 1) {
                            currentMonth.value = 12
                            currentYear.value -= 1
                        } else {
                            currentMonth.value -= 1
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_left_arrow),
                        contentDescription = "Previous Month",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Menampilkan bulan dan tahun
                Text(
                    text = "${getMonthName(currentMonth.value)} ${currentYear.value}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )

                // Tombol bulan berikutnya
                IconButton(
                    onClick = {
                        if (currentMonth.value == 12) {
                            currentMonth.value = 1
                            currentYear.value += 1
                        } else {
                            currentMonth.value += 1
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "Next Month",
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .layoutId("days")
            ) {
                listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .weight(1f)  // Setiap item dibagi rata
                            .padding(vertical = 3.dp),  // Sesuaikan padding vertikal
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), // Ukuran font lebih kecil
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.layoutId("dates")
            ) {
                val days = generateDaysInMonth(currentMonth.value, currentYear.value)

                items(days.size) { index ->
                    val day = days[index]
                    val isOutsideMonth = day.first == "0"
                    val dayNumber = day.second.toIntOrNull() ?: 0

                    // Menentukan apakah tanggal ini terpilih
                    val isSelected = selectedDay.value == dayNumber

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(41.dp, 32.dp)
                            .background(
                                if (isToday && isSelected) Color(0xFF7B61FF) else Color.Transparent,
                                shape = RectangleShape
                            )
                            .clickable {
                                if (!isOutsideMonth) {
                                    selectedDay.value =
                                        if (selectedDay.value == dayNumber) null else dayNumber
                                } else {
                                    // Pindah ke bulan yang sesuai
                                    if (index < 7) {
                                        // Jika tanggal dari bulan sebelumnya
                                        if (currentMonth.value == 1) {
                                            currentMonth.value = 12
                                            currentYear.value -= 1
                                        } else {
                                            currentMonth.value -= 1
                                        }
                                    } else {
                                        // Jika tanggal dari bulan berikutnya
                                        if (currentMonth.value == 12) {
                                            currentMonth.value = 1
                                            currentYear.value += 1
                                        } else {
                                            currentMonth.value += 1
                                        }
                                    }
                                    // Pilih tanggal yang diklik
                                    selectedDay.value = dayNumber
                                }
                            }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = day.second,
                                style = MaterialTheme.typography.bodySmall,
                                color = when {
                                    isOutsideMonth -> Color.Gray
                                    isToday && isSelected -> Color.White
                                    else -> Color.Black
                                },
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = if (isToday && isSelected) FontWeight.Bold else FontWeight.Normal
                            )

                            // Menampilkan garis bawah hanya jika tanggal ini terpilih
                            if (isSelected && !isToday && !isOutsideMonth) {
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(1.dp),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter), // Ganti dengan nama file SVG Anda
                    contentDescription = "Profile",
                    tint = Color(0xFFC5C6CC),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // Menghilangkan animasi warna abu-abu
                            onClick = {
                                showSeachCategory.value = true
                                focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                            }
                        )
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .layoutId("lazyColumn")
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                items(allSchedules) { schedule ->
                    ScheduleRow(schedule, navController)
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(bottom = 12.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .width(327.dp) // Lebar tetap 327dp
                    .height(48.dp) // Tinggi Card
                    .clip(RoundedCornerShape(12.dp))
                    .layoutId("card") // Membuat sudut atas Card melengkung
                    .alpha(1f) // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
                    .clickable { navController.navigate("new_event_screen") },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF006FFD) // Warna latar belakang Card
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Menjaga ikon dan teks sejajar secara vertikal
                    horizontalArrangement = Arrangement.Center, // Menjaga agar konten berada di tengah
                    modifier = Modifier.fillMaxSize() // Menggunakan semua ruang yang ada di dalam Card
                ) {
                    // Ikon di sebelah kiri teks
                    Image(
                        painter = painterResource(id = R.drawable.ic_add), // Ganti dengan nama gambar Anda di folder drawable
                        contentDescription = "Add Event",
                        modifier = Modifier.size(16.dp) // Sesuaikan ukuran gambar
                    )

                    // Teks "Add Event"
                    Text(
                        text = "Add Event",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White, // Mengubah warna teks menjadi hitam
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth() // Lebar penuh
                    .height(88.dp)
                    .layoutId("navBar"),
                horizontalArrangement = Arrangement.SpaceBetween, // Jarak antar tombol navbar yang sama
                verticalAlignment = Alignment.CenterVertically // Menjaga tombol tetap sejajar vertikal
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_event), // Ganti dengan nama file SVG Anda
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(96.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // Menghilangkan animasi warna abu-abu
                            onClick = {
                                navController.navigate("event_detail_screen")
                                focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                            }
                        )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_schedule), // Ganti dengan nama file SVG Anda
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(96.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // Menghilangkan animasi warna abu-abu
                            onClick = {
                                focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                            }
                        )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_settings), // Ganti dengan nama file SVG Anda
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(96.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // Menghilangkan animasi warna abu-abu
                            onClick = {
                                navController.navigate("settings_screen")
                                focusManager.clearFocus()
                            }
                        )
                )
            }
        }

        if (showSeachCategory.value) {
            AlertCategoryWindow(
                navController = navController,
                showDialog = showSeachCategory.value,
                title = "Search Category",
                onDismiss = { showSeachCategory.value = false },
                onConfirm = { showSeachCategory.value = false },
                onCancel = { showSeachCategory.value = false }
            )
        }

    }
}

@Composable
fun ScheduleRow(schedule: ScheduleItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Lebar penuh
            .width(327.dp) // Lebar tetap 327dp
            .height(72.dp) // Tinggi Card
            .clip(RoundedCornerShape(16.dp)) // Membuat sudut atas Card melengkung
            .alpha(1f) // Mengatur alpha menjadi 1 (sepenuhnya terlihat)
            .clickable { /* Action saat Card ditekan */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB4DBFF) // Warna latar belakang Card
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
                    text = schedule.name,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = schedule.time,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF71727A),
                    modifier = Modifier.padding(start = 8.dp)
                )
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
                    .width(80.dp) // Lebar tombol Detail
                    .height(40.dp) // Tinggi tombol Detail
                    .clickable { /* Action untuk detail kanan */ }
            ) {
                // Menampilkan teks pada Card Detail
                Box(
                    contentAlignment = Alignment.Center, // Memastikan tombol berada di tengah
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = { navController.navigate("home_event_detail_screen") },
                        modifier = Modifier
                            .fillMaxSize(), // Memastikan tombol memenuhi ruang
                        shape = RoundedCornerShape(12.dp), // Sudut tombol melengkung
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Warna latar belakang tombol
                            contentColor = Color(0xFF007BFF) // Warna teks di dalam tombol
                        )
                    ) {
                        Text(
                            text = "Detail", // Teks yang ada di dalam tombol
                            fontSize = 12.sp, // Ukuran font yang lebih besar
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center // Memastikan teks berada di tengah tombol
                        )
                    }
                }
            }
        }
    }
}


data class ScheduleItem(
    val name: String,
    val time: String
)

// Fungsi untuk mendapatkan nama bulan
fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        else -> "December"
    }
}

// Fungsi untuk menghasilkan jumlah hari dalam sebulan
fun generateDaysInMonth(month: Int, year: Int): List<Pair<String, String>> {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1) // Set to the first day of the month
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    val daysList = mutableListOf<Pair<String, String>>()

    // Menambahkan hari pertama yang kosong jika bulan dimulai bukan pada hari Minggu
    for (i in 1 until calendar.get(Calendar.DAY_OF_WEEK)) {
        // Mengambil tanggal dari bulan sebelumnya
        val prevMonth = (month - 1).takeIf { it > 0 } ?: 12
        val prevYear = if (month == 1) year - 1 else year
        val prevMonthDays = Calendar.getInstance().apply {
            set(prevYear, prevMonth - 1, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)

        daysList.add("0" to (prevMonthDays - (calendar.get(Calendar.DAY_OF_WEEK) - i) + 1).toString())
    }

    // Menambahkan hari-hari bulan tersebut
    for (i in 1..daysInMonth) {
        daysList.add(i.toString() to i.toString())
    }

    // Menghitung sisa slot kosong di akhir bulan (jika hari terakhir bulan bukan Sabtu)
    val remainingSlots = (7 - (daysInMonth + calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7) % 7
    // Menambahkan slot kosong jika perlu
    for (i in 1..remainingSlots) {
        // Mengambil tanggal dari bulan berikutnya
        val nextMonth = (month % 12) + 1
        val nextYear = if (month == 12) year + 1 else year

        daysList.add("0" to (i).toString()) // Mengisi ekstra slot dengan hari kosong
    }

    return daysList
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController
    )
}