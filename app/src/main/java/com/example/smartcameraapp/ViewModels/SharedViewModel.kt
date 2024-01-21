package com.example.smartcameraapp.ViewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcameraapp.MLKit.ImageLabeler
import com.example.smartcameraapp.MLKit.TextRecognizer
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _selectedPhoto = MutableStateFlow<Bitmap?>(null)
    val selectedPhoto: StateFlow<Bitmap?> = _selectedPhoto
    private val _recognizedText = MutableStateFlow<String>("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()
    private val _recognizedObjects = MutableStateFlow<String>("")
    val recognizedObjects: StateFlow<String> = _recognizedObjects.asStateFlow()

    private val imageLabeler = ImageLabeler()
    private val textRecognizer = TextRecognizer()

    fun selectPhoto(bitmap: Bitmap) {
        viewModelScope.launch {
            _selectedPhoto.emit(bitmap)
            recognizeText()
            recognizeObjects()
        }
    }

    private fun recognizeText() {
        val bitmap: Bitmap? = selectedPhoto.value
        val image = bitmap?.let { InputImage.fromBitmap(it, 0) }
        if (image != null) {
            textRecognizer.recognizeText(image) { result->_recognizedText.value=result }
        }
    }

     private fun recognizeObjects() {
         val bitmap: Bitmap? = selectedPhoto.value
         val image = bitmap?.let { InputImage.fromBitmap(it, 0) }
         if(image != null) {
             imageLabeler.recognizeObjects(image) { result->_recognizedObjects.value=result}
         }
    }
}