package com.example.smartcameraapp.Screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.smartcameraapp.ViewModels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoDetailsScreen(sharedViewModel: SharedViewModel) {

    val selectedPhoto: Bitmap? by sharedViewModel.selectedPhoto.collectAsState()
    val text: String by sharedViewModel.recognizedText.collectAsState()
    val objects : String by sharedViewModel.recognizedObjects.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            ) {
                if (selectedPhoto != null) {

                    Image(
                        bitmap = remember(selectedPhoto.hashCode()) { selectedPhoto!!.asImageBitmap() },
                        contentDescription = "Captured photo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(500.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)

                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
            Text(
                text = "Recognized Text:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = text.ifBlank { "Could not recognize any" },
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Recognized Objects:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = objects.ifBlank { "Could not recognize any" },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}