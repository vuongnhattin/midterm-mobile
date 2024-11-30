package com.example.midtermmobile.ui.component

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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.midtermmobile.R
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.CoffeeSelection
import com.example.midtermmobile.ui.component.common.CommonOrderScreen
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel

@Composable
fun OrderOnGoing(modifier: Modifier = Modifier, orderViewModel: OrderViewModel, rewardPointViewModel: RewardPointViewModel) {
    CommonOrderScreen(orderViewModel = orderViewModel, status = 2, rewardPointViewModel = rewardPointViewModel)
}
