package aps.tepatif

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import aps.tepatif.backend.BackEndAuth

@Composable
fun SettingsScreen(navController: NavController) {
    val backEndAuth = BackEndAuth()
    var showLogoutConfirm = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val constraints = ConstraintSet {
        val settingsText = createRefFor("settingsText")
        val profile = createRefFor("profile")
        val options = createRefFor("options")
        val navBar = createRefFor("navBar")

        constrain(settingsText) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(profile) {
            top.linkTo(settingsText.bottom, margin = 16.dp)
            start.linkTo(parent.start)
        }

        constrain(options) {
            top.linkTo(profile.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(navBar) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .layoutId("settingsText")
            )

            // Profile Section
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally) // Center the column horizontally
                    .layoutId("profile")
            ) {
                Box(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_picture),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(82.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit Profile",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally), // Center the text column horizontally,
                ) {
                    Text(
                        text = "Nama user",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally) // Center the user name
                    )
                    Text(
                        text = "emailuser",
                        modifier = Modifier.align(Alignment.CenterHorizontally) // Center the email
                    )
                }
            }

            // Settings Options
            SettingsOption("Saved Messages", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Recent Calls", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Devices", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Notifications", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Appearance", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Language", focusManager, navController)
            Divider(thickness = (0.5).dp)
            SettingsOption("Privacy & Security", focusManager, navController)
            Divider(thickness = (0.5).dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Menghilangkan animasi warna abu-abu
                        onClick = {
                            showLogoutConfirm.value = true
                            focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Logout", fontSize = 16.sp, color = Color(0xFFEA2020))
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
                            navController.navigate("event_detail_screen")
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
                            focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                        })
            )
        }
    }

    if (showLogoutConfirm.value) {
        ConfirmWindow(
            navController = navController,
            showDialog = showLogoutConfirm.value,
            title = "Logout Confirmation",
            content = "Are you sure you want to logout?",
            onDismiss = { showLogoutConfirm.value = false },
            onConfirm = {
                showLogoutConfirm.value = false
                backEndAuth.logout()
                navController.navigate("login_screen")
            },
            onCancel = { showLogoutConfirm.value = false }
        )
    }

}

@Composable
fun SettingsOption(text: String, focusManager: FocusManager, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Menghilangkan animasi warna abu-abu
                onClick = {
                    focusManager.clearFocus() // Menghapus fokus saat tombol ditekan
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_right_arrow),
            tint = Color(0xFF8F9098),
            contentDescription = "Arrow Forward",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController = navController)
}