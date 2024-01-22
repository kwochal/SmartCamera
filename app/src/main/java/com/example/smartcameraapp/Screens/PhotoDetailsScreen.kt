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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.smartcameraapp.ViewModels.SharedViewModel
import com.example.smartcameraapp.R

@Composable
fun PhotoDetailsScreen(sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val selectedPhoto: Bitmap? by sharedViewModel.selectedPhoto.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.medium_padding))

            ) {
                if (selectedPhoto != null) {

                    Image(
                        bitmap = remember(selectedPhoto.hashCode()) { selectedPhoto!!.asImageBitmap() },
                        contentDescription = context.getString(R.string.captured_photo),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(500.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surface)

                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.small_padding)))
                }
            }
            RecognizedElements(sharedViewModel = sharedViewModel)
        }
    }
}

@Composable
fun RecognizedElements (sharedViewModel: SharedViewModel) {
    val text: String by sharedViewModel.recognizedText.collectAsState()
    val objects : String by sharedViewModel.recognizedObjects.collectAsState()
    val context = LocalContext.current
    Text(
        text = context.getString(R.string.text_title),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.xsmall_padding)))
    Text(
        text = text.ifBlank { context.getString(R.string.nothing_recognized) },
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.small_padding)))
    Text(
        text = context.getString(R.string.objects_title),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.xsmall_padding)))
    Text(
        text = objects.ifBlank { context.getString(R.string.nothing_recognized) },
        style = MaterialTheme.typography.bodyMedium
    )

}