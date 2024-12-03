package com.example.midtermmobile.mock

import androidx.compose.ui.res.painterResource
import com.example.midtermmobile.R
import com.example.midtermmobile.model.Coffee
import com.example.midtermmobile.model.CoffeeRedeem

object CoffeeData {
    val coffeeList = listOf(
        Coffee(1, "Americano", 3.5, R.drawable.americano),
        Coffee(2, "Cappuccino", 2.5, R.drawable.cappuccino),
        Coffee(3, "Mocha", 4.0, R.drawable.mocha),
        Coffee(4, "Flat White", 4.5, R.drawable.flat_white),
    )

    fun findCoffeeById(coffeeId: Int): Coffee {
        return coffeeList.find { it.id == coffeeId } ?: coffeeList[0]
    }
}

object CoffeeRedeemData {
    val redeemList = listOf(
        CoffeeRedeem(1, 3.0),
        CoffeeRedeem(2, 1.0),
        CoffeeRedeem(3, 3.0),
        CoffeeRedeem(4, 4.0),
    )

    fun findRedeemByCoffeeId(coffeeId: Int): CoffeeRedeem {
        return redeemList.find { it.coffeeId == coffeeId } ?: redeemList[0]
    }
}