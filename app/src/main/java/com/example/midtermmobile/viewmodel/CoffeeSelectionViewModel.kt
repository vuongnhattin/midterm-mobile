package com.example.midtermmobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.midtermmobile.model.CoffeeSelection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoffeeSelectionViewModel : ViewModel() {
    private val _selection = MutableStateFlow(CoffeeSelection())
    val selection: StateFlow<CoffeeSelection> = _selection

    fun setDefaultValues() {
        _selection.value = CoffeeSelection()
    }

    fun setCoffeeId(coffeeId: Int) {
        _selection.value = _selection.value.copy(coffeeId = coffeeId)
    }

    fun increaseQuantity() {
        _selection.value = _selection.value.copy(quantity = _selection.value.quantity + 1)
    }

    fun decreaseQuantity() {
        if (_selection.value.quantity > 1) {
            _selection.value = _selection.value.copy(quantity = _selection.value.quantity - 1)
        }
    }

    fun setShotType(type: String) {
        _selection.value = _selection.value.copy(shotType = type)
    }

    fun setSelection(option: String) {
        _selection.value = _selection.value.copy(selection = option)
    }

    fun setSize(size: String) {
        _selection.value = _selection.value.copy(size = size)
    }

    fun setIceLevel(level: String) {
        _selection.value = _selection.value.copy(iceLevel = level)
    }
}
