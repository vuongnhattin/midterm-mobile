package com.example.midtermmobile.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.mock.CoffeeRedeemData
import com.example.midtermmobile.model.Coffee
import com.example.midtermmobile.model.CoffeeRedeem
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel

@Composable
fun RedeemScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    rewardPointViewModel: RewardPointViewModel,
    orderViewModel: OrderViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var redeemSuccess by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    Screen {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center // Center the title
        ) {
            // Back button
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterStart) // Align to the start
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }

            // Title
            Text(
                text = "Redeem",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center) // Ensure title stays centered
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            items(CoffeeRedeemData.redeemList) { redeem ->
                RedeemItem(redeem = redeem, onClick = {
                    val result = rewardPointViewModel.redeemPoints(redeem.points)
                    redeemSuccess = result
                    showDialog = true
                    if (result) {
                        dialogMessage = "Redeem successful!"
                        val coffeeSelection = CoffeeSelection(coffeeId = redeem.coffeeId)
                        orderViewModel.addCoffeeToCart(coffeeSelection)
                        orderViewModel.checkout(coffeeSelection)
                    } else {
                        dialogMessage = "Not enough points!"
                    }
                })
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false }, // Close when clicking outside
                title = { Text("Message") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
            )
        }
    }
}

@Composable
fun RedeemItem(redeem: CoffeeRedeem, onClick: () -> Unit) {
    val coffee: Coffee = CoffeeData.findCoffeeById(redeem.coffeeId)
    Row(
        modifier = Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painterResource(coffee.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
        )
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Text(
                text = coffee.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Valid until 04.01.21",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "${redeem.points} pts",
            )
        }
    }
}

@Preview
@Composable
fun PreviewRedeemScreen() {
    RedeemScreen(navController = rememberNavController(), rewardPointViewModel = viewModel(), orderViewModel = viewModel())
}