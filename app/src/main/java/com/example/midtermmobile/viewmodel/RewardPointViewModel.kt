package com.example.midtermmobile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RewardPointViewModel : ViewModel() {
    private var _rewardPoints = MutableStateFlow(0.0)
    val rewardPoints: StateFlow<Double> = _rewardPoints

    fun addPoints(points: Double) {
        _rewardPoints.value += points
    }

    fun redeemPoints(points: Double): Boolean {
        if (_rewardPoints.value >= points) {
            _rewardPoints.value -= points
            return true
        }
        return false
    }
}