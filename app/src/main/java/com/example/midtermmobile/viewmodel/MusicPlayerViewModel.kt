package com.example.midtermmobile.viewmodel

import android.app.Application
import android.media.MediaPlayer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.midtermmobile.R

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {
    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = mutableStateOf(false)
    val isPlaying: State<Boolean> get() = _isPlaying

    init {
        mediaPlayer = MediaPlayer.create(application, R.raw.pickleball) // Replace with your music file
    }

    fun togglePlayback() {
        mediaPlayer?.let {
            if (_isPlaying.value) {
                it.pause()
            } else {
                it.start()
            }
            _isPlaying.value = !_isPlaying.value // Update the state
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}