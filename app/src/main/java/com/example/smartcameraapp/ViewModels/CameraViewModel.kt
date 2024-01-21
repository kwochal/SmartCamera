package com.example.smartcameraapp.ViewModels

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

data class CameraState(
    val capturedImage: Bitmap? = null,
)

class CameraViewModel: ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun savePhoto(bitmap: Bitmap) {
        viewModelScope.launch {
            _state.value.capturedImage?.recycle()
            _state.value = _state.value.copy(capturedImage = bitmap)
        }
    }



    fun capturePhoto(
        context: Context,
        cameraController: LifecycleCameraController,
        onPhotoCaptured: (Bitmap) -> Unit
    ) {
        val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

        cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val correctedBitmap: Bitmap = image
                    .toBitmap()

                onPhotoCaptured(correctedBitmap)
                image.close()
            }


        })
    }


    override fun onCleared() {
        _state.value.capturedImage?.recycle()
        super.onCleared()
    }
}