package aps.tepatif

import NewEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar


@Composable
fun TimePicker(
    onTimeChanged: (String) -> Unit
) {
    // Get the current time
    val currentTime = Calendar.getInstance()
    val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentTime.get(Calendar.MINUTE)
    val currentAMPM = if (currentTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"

    var selectedHour = remember { mutableStateOf(currentHour) }
    var selectedMinute = remember { mutableStateOf(currentMinute) }
    var selectedAMPM = remember { mutableStateOf(currentAMPM) }

    val hours = (1..12).toList() // Jam 1-12
    val minutes = (0..59).toList() // Menit 0-59
    val ampm = listOf("AM", "PM") // AM / PM

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Picker untuk jam
        WheelPicker(
            items = hours,
            selectedItem = selectedHour.value,
            onItemSelected = { selectedHour.value = it as Int }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Picker untuk menit
        WheelPicker(
            items = minutes,
            selectedItem = selectedMinute.value,
            onItemSelected = { selectedMinute.value = it as Int }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Picker untuk AM/PM
        WheelPicker(
            items = ampm,
            selectedItem = selectedAMPM.value,
            onItemSelected = { selectedAMPM.value = it.toString() }
        )
    }

    // Format waktu yang telah dipilih
    LaunchedEffect(selectedHour, selectedMinute, selectedAMPM) {
        onTimeChanged(
            "${
                selectedHour.value.toString().padStart(2, '0')
            }:${selectedMinute.value.toString().padStart(2, '0')} ${selectedAMPM.value}"
        )
    }
}

@Composable
fun WheelPicker(
    items: List<Any>,  // Ganti dari List<Int> menjadi List<Any> untuk mendukung berbagai tipe data
    selectedItem: Any,  // Ganti dari Int menjadi Any
    onItemSelected: (Any) -> Unit
) {
    val listState = rememberLazyListState()

    // Initialize the scroll position to the current selected item
    val selectedIndex = remember {
        mutableStateOf(items.indexOf(selectedItem).takeIf { it >= 0 } ?: 0)
    }

    LaunchedEffect(Unit) {
        // Adjust the scroll position to the selected item, starting from the middle of the list
        listState.scrollToItem((items.size * 50)) // Start at an offset
    }

    // Infinite scroll logic: Loop the scroll once it reaches the start or the end
    Row {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .height(150.dp)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(Int.MAX_VALUE) { index ->
                val currentItem = items[index % items.size]
                Text(
                    text = currentItem.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            val actualIndex = index % items.size
                            selectedIndex.value = actualIndex
                            onItemSelected(currentItem)
                        },
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerPreview() {
    // Here we just need to call TimePicker directly in the preview without navigation
    TimePicker(onTimeChanged = { time ->
        // Handle the time change
    })
}