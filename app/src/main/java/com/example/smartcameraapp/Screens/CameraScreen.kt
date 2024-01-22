package com.example.smartcameraapp.Screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

import com.example.smartcameraapp.ViewModels.CameraViewModel
import com.example.smartcameraapp.ViewModels.SharedViewModel
import com.example.smartcameraapp.R



@Composable
fun CameraScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
        val cameraViewModel: CameraViewModel = viewModel()
        val capturedImage: Bitmap? by cameraViewModel.capturedImage.collectAsState()

        Camera(
            onPhotoCaptured = cameraViewModel::savePhoto,
            lastCapturedPhoto = capturedImage,
            navController = navController,
            sharedViewModel = sharedViewModel,
            cameraViewModel = cameraViewModel
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Camera(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null,
    navController : NavHostController,
    sharedViewModel: SharedViewModel,
    cameraViewModel : CameraViewModel
    ) {

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {},
                onClick = { cameraViewModel.capturePhoto(context, cameraController, onPhotoCaptured) },
                icon = { },
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }
    ) { paddingValues: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                }
            )

            if (lastCapturedPhoto != null) {
                CapturedPhoto(
                    modifier = Modifier.align(alignment = BottomEnd),
                    lastCapturedPhoto = lastCapturedPhoto,
                    onPhotoClicked = {
                        sharedViewModel.selectPhoto(lastCapturedPhoto)
                        navController.navigate("photoDetail")
                    }
                )
            }
        }
    }

}

@Composable
private fun CapturedPhoto(modifier: Modifier = Modifier, lastCapturedPhoto: Bitmap,
    onPhotoClicked: () -> Unit)
{
    val capturedPhoto: ImageBitmap = remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }
    val context = LocalContext.current
    Card(
        modifier = modifier
            .size(120.dp)
            .padding(dimensionResource(R.dimen.medium_padding)),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.clickable { onPhotoClicked() }
        ) {
            Image(
                bitmap = capturedPhoto,
                contentDescription = context.getString(R.string.captured_photo),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        }
    }
}

