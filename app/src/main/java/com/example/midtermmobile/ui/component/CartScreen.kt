package com.example.midtermmobile.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.viewmodel.OrderViewModel

@Composable
fun CartScreen(modifier: Modifier = Modifier, navController: NavController = rememberNavController(), orderViewModel: OrderViewModel) {
    val cartItems by orderViewModel.orders.collectAsState()
    Log.i("CartScreen", "cartItems: $cartItems")
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CartHeader(navController = navController)
            CartDetails(cartItems = cartItems, orderViewModel = orderViewModel)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                CartBottom(cartItems = cartItems, navController, orderViewModel)
            }
        }


    }
}

@Composable
fun CartBottom(cartItems: List<CoffeeSelection>, navController: NavController, orderViewModel: OrderViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = "Total price", style = MaterialTheme.typography.titleSmall)
            Text(text = "$${orderViewModel.calculateCartPrice()}", style = MaterialTheme.typography.titleLarge)
        }
        Button(
            onClick = {
                orderViewModel.checkout()
                navController.navigate("order-success")
            },
        ) {
            Text(text = "Checkout")
        }
    }
}

@Composable
fun CartDetails(cartItems: List<CoffeeSelection>, orderViewModel: OrderViewModel) {
    if (cartItems.isEmpty()) {
        Text(text = "Your cart is empty!")
    } else {
        // Display the list of items in the cart
        LazyColumn {
            items(cartItems) { item ->
                if (item.status == 1) {
                    CartItemRow(coffeeSelection = item, orderViewModel = orderViewModel)
                }
            }
        }
    }
}

@Composable
fun CartHeader(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() }, // Navigate back
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Material Design back arrow
            contentDescription = "Back"
        )
    }
    Text(
        text = "My Cart",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun CartItemRow(coffeeSelection: CoffeeSelection, orderViewModel: OrderViewModel) {
    val coffee = CoffeeData.findCoffeeById(coffeeSelection.coffeeId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount < -100) { // Swipe left
                        orderViewModel.removeCoffeeFromCart(coffeeSelection)
                    }
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = coffee.imageRes),
            contentDescription = "Coffee image",
            modifier = Modifier.size(60.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = coffee.name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = "${coffeeSelection.shotType} | ${coffeeSelection.selection} | ${coffeeSelection.size} | ${coffeeSelection.iceLevel}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = "X${coffeeSelection.quantity}", style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "$${coffeeSelection.calculatePrice()}", style = MaterialTheme.typography.titleMedium)
        }
        IconButton(
            onClick = {
                orderViewModel.removeCoffeeFromCart(coffeeSelection)
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.Clear, // Material Design back arrow
                contentDescription = "Remove"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartScreenPreview() {
    val orderViewModel: OrderViewModel = viewModel()
    orderViewModel.addCoffeeToCart(CoffeeSelection(1, 2))
    CartScreen(orderViewModel = orderViewModel)
}