package aps.tepatif

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import generateDaysInMonth
import getMonthName
import java.util.Calendar

@Composable
fun SelectDateWindow(
    navController: NavController,
    clicked: (String) -> Unit
) {
    var selectedDay = remember { mutableStateOf<Int?>(null) }
    var showDialog = remember { mutableStateOf(true) }

    val calendar = Calendar.getInstance()
    val currentMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1) }
    val currentYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(253.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                // Header bar (Bulan dan Tahun)
                Row(
                    modifier = Modifier.size(303.dp, 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (currentMonth.value == 1) {
                            currentMonth.value = 12
                            currentYear.value -= 1
                        } else {
                            currentMonth.value -= 1
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left_arrow),
                            contentDescription = "Previous Month",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "${getMonthName(currentMonth.value)} ${currentYear.value}",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {
                        if (currentMonth.value == 12) {
                            currentMonth.value = 1
                            currentYear.value += 1
                        } else {
                            currentMonth.value += 1
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_right_arrow),
                            contentDescription = "Next Month",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Header Hari
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.size(303.dp, 20.dp)
                ) {
                    listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 3.dp),
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Grid Kalender
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.size(303.dp, 163.dp)
                ) {
                    val days = generateDaysInMonth(currentMonth.value, currentYear.value)

                    items(days.size) { index ->
                        val day = days[index]
                        val isOutsideMonth = day.first == "0"
                        val dayNumber = day.second.toIntOrNull() ?: 0

                        // Status untuk warna sentuhan
                        val isPressed = remember { mutableStateOf(false) }
                        val isSelected = selectedDay.value == dayNumber

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(41.dp, 32.dp)
                                .background(
                                    color = when {
                                        isPressed.value -> Color(0xFF7B61FF) // Warna saat disentuh
                                        else -> Color.Transparent
                                    },
                                    shape = RectangleShape
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            isPressed.value = true
                                            tryAwaitRelease()
                                            isPressed.value = false
                                        },
                                        onTap = {
                                            if (!isOutsideMonth) {
                                                selectedDay.value = dayNumber
                                                val selectedDate =
                                                    "${getMonthName(currentMonth.value).take(3)} ${day.second}, ${currentYear.value}"
                                                clicked(selectedDate)
                                                showDialog.value = false
                                            } else {
                                                // Navigasi antar bulan
                                                if (index < 7) {
                                                    if (currentMonth.value == 1) {
                                                        currentMonth.value = 12
                                                        currentYear.value -= 1
                                                    } else {
                                                        currentMonth.value -= 1
                                                    }
                                                } else {
                                                    if (currentMonth.value == 12) {
                                                        currentMonth.value = 1
                                                        currentYear.value += 1
                                                    } else {
                                                        currentMonth.value += 1
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                        ) {
                            Text(
                                text = day.second,
                                style = MaterialTheme.typography.bodySmall,
                                color = when {
                                    isOutsideMonth -> Color.Gray // Hari di luar bulan
                                    isPressed.value -> Color.White // Warna putih saat disentuh atau dipilih
                                    else -> Color.Black // Warna default
                                },
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDateWindowPreview() {
    val navController = rememberNavController()
    SelectDateWindow(
        navController = navController,
        clicked = { }
    )
}