package com.example.midtermmobile.model

import com.example.midtermmobile.mock.CoffeeData

data class CoffeeSelection(
    var coffeeId: Int = 1,
    var quantity: Int = 1,
    var shotType: String = "Single",
    var selection: String = "Hot",
    var size: String = "Medium",
    var iceLevel: String = "Little",
    var status: Int = 1
) {
    fun calculatePrice(): Double {
        return quantity * CoffeeData.findCoffeeById(coffeeId).price
    }
}