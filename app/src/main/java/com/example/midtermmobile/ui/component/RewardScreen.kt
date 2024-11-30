package com.example.midtermmobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.ui.component.common.BottomNavigationBar
import com.example.midtermmobile.ui.component.common.LoyaltyCard
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel

@Composable
fun RewardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    rewardPointViewModel: RewardPointViewModel,
    orderViewModel: OrderViewModel
) {
    val orders = orderViewModel.orders.collectAsState().value
    println(orders)
    val rewardPoints = rewardPointViewModel.rewardPoints.collectAsState().value
    Screen {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Rewards", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))
        LoyaltyCard(orderViewModel = orderViewModel)

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column() {
                    Text(
                        text = "My Points: ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = rewardPoints.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(
                    onClick = {
                        navController.navigate("redeem")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "Redeem drinks"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "History Rewards",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.height(250.dp)
        ) {
            items(orders) {order ->
                if (order.status == 3) {
                    HistoryRewardItem(order)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }

        BottomNavigationBar("reward", navController)
    }
}

@Composable
fun HistoryRewardItem(order: CoffeeSelection) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = "${CoffeeData.findCoffeeById(order.coffeeId).name} X ${order.quantity}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "24 June | 12:30 PM",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            text = "+ ${order.calculatePrice()} Pts",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RewardScreenPreview() {
    val orderViewModel: OrderViewModel = viewModel()
    val coffee1 = CoffeeSelection(1)
    val coffee2 = CoffeeSelection(2)
    orderViewModel.addCoffeeToCart(coffee1)
    orderViewModel.addCoffeeToCart(coffee2)
    orderViewModel.moveToHistory(coffee1)
    orderViewModel.moveToHistory(coffee2)
    RewardScreen(
        navController = rememberNavController(),
        rewardPointViewModel = viewModel(),
        orderViewModel = viewModel()
    )
}