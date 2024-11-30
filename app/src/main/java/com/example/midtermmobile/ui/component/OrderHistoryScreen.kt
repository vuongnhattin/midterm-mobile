package com.example.midtermmobile.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.midtermmobile.ui.component.common.CommonOrderScreen
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel

@Composable
fun OrderHistory(modifier: Modifier = Modifier, orderViewModel: OrderViewModel, rewardPointViewModel: RewardPointViewModel) {
    CommonOrderScreen(orderViewModel = orderViewModel, status = 3, rewardPointViewModel = rewardPointViewModel)
}