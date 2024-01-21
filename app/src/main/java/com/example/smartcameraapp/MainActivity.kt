package com.example.smartcameraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.smartcameraapp.Screens.CameraScreen
import com.example.smartcameraapp.Screens.GrantPermissionScreen
import com.example.smartcameraapp.ViewModels.SharedViewModel
import com.example.smartcameraapp.ui.theme.SmartCameraAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartCameraAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }




    private fun startCamera() {
        // Your camera initialization code here
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel : SharedViewModel) {

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    navController: NavHostController,
    viewModel: SharedViewModel
) {

    if (hasPermission) {
        CameraScreen(navController, viewModel)
    }
    else{
        GrantPermissionScreen(onRequestPermission)
    }
}








@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartCameraAppTheme {
        Greeting("Android")
    }
}