package com.example.smartcameraapp.Screens

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.smartcameraapp.ViewModels.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel : SharedViewModel) {

    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val hasPermission = cameraPermissionState.status.isGranted
    val onRequestPermission = cameraPermissionState::launchPermissionRequest

    if (hasPermission) {
        CameraScreen(navController, viewModel)
    }
    else{
        GrantPermissionScreen(onRequestPermission)
    }
}
