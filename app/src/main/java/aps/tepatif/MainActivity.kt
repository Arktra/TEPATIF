package aps.tepatif

import EditEventScreen
import EventDetail
import HomeEventDetail
import aps.tepatif.backend.BackEndAuth

import HomeScreen
import LoginScreen
import NewEvent
import SignUpScreen
import OTPValidationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import aps.tepatif.ui.theme.TEPATIFTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TEPATIFTheme {
                val navController = rememberNavController()
                val backEndAuth = BackEndAuth()

                NavHost(navController = navController, startDestination = "login_screen") {
                    composable("login_screen") {
                        LoginScreen(navController = navController, backEndAuth = backEndAuth)
                    }

                    composable("sign_up_screen") {
                        SignUpScreen(navController = navController, backEndAuth = backEndAuth)
                    }

                    composable("otp_validation") {
                        OTPValidationScreen(navController = navController)
                    }

                    composable("home_screen") {
                        HomeScreen(navController = navController)
                    }

                    composable("new_event_screen") {
                        NewEvent(navController = navController)
                    }

                    composable("home_event_detail_screen") {
                        HomeEventDetail(navController = navController)
                    }

                    composable("edit_event_screen") {
                        EditEventScreen(navController = navController)
                    }

                    composable("settings_screen") {
                        SettingsScreen(navController = navController)
                    }

                    composable("event_detail_screen") {
                        EventDetail(navController = navController)
                    }
                }
            }
        }
    }
}