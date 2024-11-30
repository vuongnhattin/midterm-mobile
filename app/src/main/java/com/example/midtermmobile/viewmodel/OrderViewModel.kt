package com.example.midtermmobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<CoffeeSelection>>(emptyList())
    val orders: StateFlow<List<CoffeeSelection>> = _orders

    private val _loyalCards = MutableStateFlow(0)
    val loyalCards: StateFlow<Int> = _loyalCards

    fun calculateOrderedPrice(): Double {
        var total = 0.0
        for (item in _orders.value) {
            if (item.status == 3) {
                val quantity = item.quantity
                val price = CoffeeData.findCoffeeById(item.coffeeId).price
                total += price * quantity
            }
        }
        return total
    }

    fun moveToHistory(item: CoffeeSelection) {
        val updatedOrders = _orders.value.toMutableList().apply {
            val index = indexOf(item)
            if (index != -1) {
                this[index] = item.copy(status = 3) // Update status
            }
        }
        _orders.value = updatedOrders // Emit updated list

        _loyalCards.value += item.quantity
        if (_loyalCards.value > 8) {
            _loyalCards.value = 8
        }
    }

    fun resetLoyalCard() {
        if (_loyalCards.value == 8) {
            _loyalCards.value = 0
        }
    }

    fun calculateCartPrice(): Double {
        var total = 0.0
        for (item in _orders.value) {
            if (item.status == 1) {
                val quantity = item.quantity
                val price = CoffeeData.findCoffeeById(item.coffeeId).price
                total += price * quantity
            }
        }
        return total
    }

    // Add coffee to the cart
    fun addCoffeeToCart(coffeeSelection: CoffeeSelection) {
        _orders.value += coffeeSelection
    }

    // Remove coffee from the cart (if needed)
    fun removeCoffeeFromCart(coffeeSelection: CoffeeSelection) {
        _orders.value -= coffeeSelection
    }

    // Clear cart
    fun checkout() {
        for (item in _orders.value) {
            if (item.status == 1) {
                item.status = 2
            }
        }
    }

    fun checkout(coffeeSelection: CoffeeSelection) {
        val updatedOrders = _orders.value.toMutableList().apply {
            val index = indexOf(coffeeSelection)
            if (index != -1) {
                this[index] = coffeeSelection.copy(status = 2) // Update status
            }
        }
        _orders.value = updatedOrders // Emit updated list
    }
}
