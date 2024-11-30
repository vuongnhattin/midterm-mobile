package com.example.midtermmobile.ui.component

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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.R
import com.example.midtermmobile.mock.CoffeeData
import com.example.midtermmobile.model.Coffee
import com.example.midtermmobile.ui.component.common.BottomNavigationBar
import com.example.midtermmobile.ui.component.common.LoyaltyCard
import com.example.midtermmobile.ui.theme.MidtermMobileTheme
import com.example.midtermmobile.viewmodel.OrderViewModel

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel
) {
    Column(modifier = Modifier.fillMaxSize()
        .padding(top = 24.dp)) {
        HomeScreenHeader(navController = navController, orderViewModel = orderViewModel)
        HomeScreenBody(navController = navController)
    }
}

@Composable
fun HomeScreenHeader(modifier: Modifier = Modifier, navController: NavController, orderViewModel: OrderViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        GreetingSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
        LoyaltyCard(orderViewModel)
    }
}

@Composable
fun GreetingSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Good morning\nAnderson",
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            lineHeight = 24.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                navController.navigate("cart")
            }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = {
                navController.navigate("profile")
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onBackground
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