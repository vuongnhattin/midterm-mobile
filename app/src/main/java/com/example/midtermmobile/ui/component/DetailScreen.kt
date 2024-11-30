package com.example.midtermmobile.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.CoffeeSelectionViewModel

@Composable
fun DetailScreen(
    navController: NavController = rememberNavController(),
    coffeeSelectionViewModel: CoffeeSelectionViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel(),
    coffeeId: Int
) {
    val coffee = CoffeeData.findCoffeeById(coffeeId)

    Log.i("DetailScreen", "Coffee ID: $coffeeId")

    coffeeSelectionViewModel.setDefaultValues()
    coffeeSelectionViewModel.setCoffeeId(coffeeId)
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DetailScreenHeader(navController)
            DetailScreenPreviewImage(coffee.imageRes)
            DetailScreenOrderSection(coffeeSelectionViewModel)
            DetailScreenBottom(coffeeSelectionViewModel, orderViewModel, navController = navController)
        }
    }
}

@Composable
fun DetailScreenBottom(coffeeSelectionViewModel: CoffeeSelectionViewModel, orderViewModel: OrderViewModel, navController: NavController) {
    val order = coffeeSelectionViewModel.selection.collectAsState().value
    val coffee = CoffeeData.findCoffeeById(order.coffeeId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$${order.quantity * coffee.price}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(
            onClick = {
                val coffeeSelection = CoffeeSelection(
                    coffeeId = order.coffeeId,
                    quantity = order.quantity,
                    shotType = order.shotType,
                    selection = order.selection,
                    size = order.size,
                    iceLevel = order.iceLevel
                )
                orderViewModel.addCoffeeToCart(coffeeSelection)
                Log.i("DetailScreen", "Added to cart: $coffeeSelection")
                Log.i("DetailScreen", "Cart size: ${orderViewModel.orders.value.size}")
                navController.navigate("cart")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add to cart")
        }
    }
}

@Composable
fun DetailScreenOrderSection(
    viewModel: CoffeeSelectionViewModel = viewModel()
) {
    val order = viewModel.selection.collectAsState().value
    val coffee = CoffeeData.findCoffeeById(order.coffeeId)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Coffee Quantity
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = coffee.name)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.decreaseQuantity() }) {
                    Text(text = "-")
                }
                Text(text = order.quantity.toString())
                IconButton(onClick = { viewModel.increaseQuantity() }) {
                    Text(text = "+")
                }
            }
        }

        // Shot Selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Shot")
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = { viewModel.setShotType("Single") },
                ) {
                    Text(
                        text = "Single",
                        color = if (order.shotType == "Single") MaterialTheme.colorScheme.primary else Color.Gray,
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedButton(
                    onClick = { viewModel.setShotType("Double") },
                ) {
                    Text(
                        text = "Double",
                        color = if (order.shotType == "Double") MaterialTheme.colorScheme.primary else Color.Gray,
                    )
                }
            }
        }

        // Selection (Hot/Cold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Select")
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(
                    onClick = { viewModel.setSelection("Hot") },
                ) {
                    Text(text = "Hot", color = if (order.selection == "Hot") MaterialTheme.colorScheme.primary else Color.Gray)
                }
                TextButton(
                    onClick = { viewModel.setSelection("Cold") },
                ) {
                    Text(text = "Cold", color = if (order.selection == "Cold") MaterialTheme.colorScheme.primary else Color.Gray)
                }
            }
        }

        // Size Selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Size")
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(
                    onClick = { viewModel.setSize("Small") },
                ) {
                    Text(text = "Small", color = if (order.size == "Small") MaterialTheme.colorScheme.primary else Color.Gray)
                }
                TextButton(
                    onClick = { viewModel.setSize("Medium") },
                ) {
                    Text(text = "Medium", color = if (order.size == "Medium") MaterialTheme.colorScheme.primary else Color.Gray)
                }
                TextButton(
                    onClick = { viewModel.setSize("Large") },
                ) {
                    Text(text = "Large", color = if (order.size == "Large") MaterialTheme.colorScheme.primary else Color.Gray)
                }
            }
        }

        // Ice Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Ice")
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(
                    onClick = { viewModel.setIceLevel("Little") },
                ) {
                    Text(
                        text = "Little",
                        color = if (order.iceLevel == "Little") MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }

                TextButton(
                    onClick = { viewModel.setIceLevel("Medium") },
                ) {
                    Text(
                        text = "Medium",
                        color = if (order.iceLevel == "Medium") MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }

                TextButton(
                    onClick = { viewModel.setIceLevel("A lot") },
                ) {
                    Text(
                        text = "A lot",
                        color = if (order.iceLevel == "A lot") MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
    }
}



@Composable
fun DetailScreenPreviewImage(imageRes: Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = imageRes!!,
            ),
            modifier = Modifier.
                size(200.dp),
            contentDescription = null
        )
    }
}

@Composable
private fun DetailScreenHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = { navController.popBackStack() }, // Navigate back
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Material Design back arrow
                contentDescription = "Back"
            )
        }
        Text("Details")
        IconButton(onClick = {
            navController.navigate("cart")
        }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    DetailScreen(coffeeId = 1)
}