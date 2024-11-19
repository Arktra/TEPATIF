package aps.tepatif

import HomeScreen
import LoginScreen
import NewEvent
import SignUpScreen
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

                NavHost(navController = navController, startDestination = "login_screen") {
                    composable("login_screen") {
                        LoginScreen(navController = navController) // Passing NavController here
                    }

                    composable("sign_up_screen") {
                        SignUpScreen(navController = navController)
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

                    composable("confirm_window") {
                        ConfirmWindow(navController = navController)
                    }
                }
            }
        }
    }
}