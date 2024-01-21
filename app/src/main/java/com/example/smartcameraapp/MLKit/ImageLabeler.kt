package com.example.smartcameraapp.MLKit

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class ImageLabeler {

    private val labeler = ImageLabeling.getClient(
        ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
    )

    fun recognizeObjects(image : InputImage, onSuccess : (String)->Unit ) {
        var resultText = ""
        labeler.process(image)
            .addOnSuccessListener { labels ->
                for (label in labels) {
                    val text = label.text
                    val confidence = label.confidence
                    resultText+="Label: $text\nConfidence: $confidence\n\n"
                }
                onSuccess(resultText)
            }
    }

}