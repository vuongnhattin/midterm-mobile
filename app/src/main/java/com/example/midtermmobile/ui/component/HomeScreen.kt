package com.example.midtermmobile.ui.component

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.R
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.Coffee
import com.example.midtermmobile.ui.component.common.BottomNavigationBar
import com.example.midtermmobile.ui.component.common.LoyaltyCard
import com.example.midtermmobile.ui.theme.MidtermMobileTheme
import com.example.midtermmobile.viewmodel.MusicPlayerViewModel
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.UserInfoViewModel

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel = viewModel(),
    userInfoViewModel: UserInfoViewModel = viewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = viewModel()
) {
    val user = userInfoViewModel.info.collectAsState().value
    Column(modifier = Modifier.fillMaxSize()
        .padding(top = 24.dp)) {
        HomeScreenHeader(navController = navController, orderViewModel = orderViewModel, name = user.fullName, musicPlayerViewModel = musicPlayerViewModel)
        HomeScreenBody(navController = navController)
    }
}

@Composable
fun HomeScreenHeader(modifier: Modifier = Modifier, navController: NavController, orderViewModel: OrderViewModel, name: String, musicPlayerViewModel: MusicPlayerViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        GreetingSection(navController, name, musicPlayerViewModel = musicPlayerViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        LoyaltyCard(orderViewModel)
    }
}

@Composable
fun GreetingSection(navController: NavController, name: String, musicPlayerViewModel: MusicPlayerViewModel) {
    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()

    // Observe the playing state
    val isPlaying = musicPlayerViewModel.isPlaying.value
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Good morning\n$name",
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            lineHeight = 24.sp
        )
        IconButton(
            onClick = {
               musicPlayerViewModel.togglePlayback()
            }
        ) {
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.pause) else painterResource(R.drawable.play),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                navController.navigate("cart")
            }) {
                Icon(
                    painterResource(R.drawable.cart),
                    contentDescription = "Cart",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate("profile")
            }) {
                Icon(
                    painterResource(R.drawable.user),
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}



@Composable
fun HomeScreenBody(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CoffeeSelectionSection(navController)
        BottomNavigationBar("home", navController)
    }
}

@Composable
fun CoffeeSelectionSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Choose your coffee",
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Set the grid to have 2 columns
            contentPadding = PaddingValues(16.dp), // Optional padding
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Spacing between columns
            verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between rows
        ) {
            items(CoffeeData.coffeeList) { coffee ->
                CoffeeOption(coffee, navController)
            }
        }
    }
}

@Composable
fun CoffeeOption(coffee: Coffee, navController: NavController) {
    Column(
        modifier = Modifier
            .size(140.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                navController.navigate("detail/${coffee.id}")
            }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = coffee.imageRes),
                contentDescription = coffee.name,
                modifier = Modifier
                    .size(80.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = coffee.name,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MidtermMobileTheme {
        HomeScreen(orderViewModel = OrderViewModel())
    }
}