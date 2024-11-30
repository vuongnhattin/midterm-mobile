package com.example.midtermmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.MockUserInfo
import com.example.midtermmobile.model.UserInfo
import com.example.midtermmobile.ui.component.CartScreen
import com.example.midtermmobile.ui.component.DetailScreen
import com.example.midtermmobile.ui.component.HomeScreen
import com.example.midtermmobile.ui.component.OrderScreen
import com.example.midtermmobile.ui.component.OrderSuccessScreen
import com.example.midtermmobile.ui.component.ProfileScreen
import com.example.midtermmobile.ui.component.RedeemScreen
import com.example.midtermmobile.ui.component.RewardScreen
import com.example.midtermmobile.ui.theme.MidtermMobileTheme
import com.example.midtermmobile.viewmodel.OrderViewModel
import com.example.midtermmobile.viewmodel.CoffeeSelectionViewModel
import com.example.midtermmobile.viewmodel.RewardPointViewModel
import com.example.midtermmobile.viewmodel.UserInfoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MidtermMobileTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val coffeeSelectionViewModel: CoffeeSelectionViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val rewardPointViewModel: RewardPointViewModel = viewModel()
    val userInfoViewModel: UserInfoViewModel = viewModel()

    userInfoViewModel.setUser(MockUserInfo.userInfo)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, orderViewModel =  orderViewModel) }
        composable("detail/{coffeeId}") { backStackEntry ->
            val coffeeId = backStackEntry.arguments?.getString("coffeeId")?.toIntOrNull() ?: 0
            DetailScreen(
                navController = navController,
                coffeeId = coffeeId,
                coffeeSelectionViewModel = coffeeSelectionViewModel,
                orderViewModel = orderViewModel
            )
        }
        composable("cart") {
            CartScreen(navController = navController, orderViewModel = orderViewModel)
        }
        composable("order-success") {
            OrderSuccessScreen(navController = navController)
        }
        composable("order") {
            OrderScreen(
                navController = navController,
                orderViewModel = orderViewModel,
                rewardPointViewModel = rewardPointViewModel
            )
        }
        composable("reward") {
            RewardScreen(
                navController = navController,
                rewardPointViewModel = rewardPointViewModel,
                orderViewModel = orderViewModel
            )
        }
        composable("redeem") {
            RedeemScreen(
                navController = navController,
                rewardPointViewModel = rewardPointViewModel,
                orderViewModel = orderViewModel
            )
        }
        composable("profile") {
            ProfileScreen(
                navController = navController,
                userInfoViewModel = userInfoViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MidtermMobileTheme {
        MyApp()
    }
}