package com.example.smartcameraapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartcameraapp.R


@Composable
fun GrantPermissionScreen(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onRequestPermission) {
            Text(text = LocalContext.current.getString(R.string.grant_permission))
        }
    }
}

@Composable
@Preview
fun GrantPermissionScreenPreview() {
    GrantPermissionScreen(onRequestPermission = {})
}