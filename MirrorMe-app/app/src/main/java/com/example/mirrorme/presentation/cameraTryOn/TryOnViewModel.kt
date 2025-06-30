package com.example.mirrorme.presentation.cameraTryOn

import androidx.lifecycle.ViewModel
import com.example.mirrorme.data.tryOn.PoseLandmarkerHelper

/**
 *  This ViewModel is used to store pose landmarker helper settings
 */
class MainViewModel : ViewModel() {
    val delegate = PoseLandmarkerHelper.DELEGATE_CPU
    val model = PoseLandmarkerHelper.MODEL_POSE_LANDMARKER_FULL
    val minDetection = PoseLandmarkerHelper.DEFAULT_POSE_DETECTION_CONFIDENCE
    val minTracking = PoseLandmarkerHelper.DEFAULT_POSE_TRACKING_CONFIDENCE
    val minPresence = PoseLandmarkerHelper.DEFAULT_POSE_PRESENCE_CONFIDENCE
}
