package com.example.smartcameraapp
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcameraapp.Screens.PhotoDetailsScreen
import com.example.smartcameraapp.ViewModels.SharedViewModel
import com.example.smartcameraapp.ui.theme.SmartCameraAppTheme

@Composable
fun App() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    SmartCameraAppTheme {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") { MainScreen(navController, sharedViewModel) }
            composable("photoDetail") { PhotoDetailsScreen(sharedViewModel) }
        }
    }
}
