package com.example.midtermmobile.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.midtermmobile.R
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.ui.component.Screen
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel

@Composable
fun CommonOrderScreen(modifier: Modifier = Modifier, status: Int = 2, orderViewModel: OrderViewModel, rewardPointViewModel: RewardPointViewModel) {
    val orders = orderViewModel.orders.collectAsState().value
    Screen {
        LazyColumn(
        ) {
            items(orders) { item ->
                if (item.status == status) {
                    OrderItem(item = item, orderViewModel = orderViewModel, rewardPointViewModel = rewardPointViewModel)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    item: CoffeeSelection,
    orderViewModel: OrderViewModel,
    rewardPointViewModel: RewardPointViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
            .clickable {
                rewardPointViewModel.addPoints(item.calculatePrice())
                orderViewModel.moveToHistory(item)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "24 June | 12:30 PM"
                )
                Text(
                    text = "$${item.calculatePrice()}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Icon(
                    painterResource(R.drawable.order_cup),
                    contentDescription = "Cup of coffee",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${CoffeeData.findCoffeeById(item.coffeeId).name} X ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Icon(
                    painterResource(R.drawable.map_pin),
                    contentDescription = "Location",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "3 Addersion Court Chino Hills, HO56824, United State",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}