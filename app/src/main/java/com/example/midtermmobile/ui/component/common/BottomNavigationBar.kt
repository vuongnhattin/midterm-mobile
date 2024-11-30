package com.example.midtermmobile.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(selecting: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        NavigationBar(
            tonalElevation = 4.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        ) {
            NavigationBarItem(
                selected = selecting == "home",
                onClick = {
                    navController.navigate("home")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
            NavigationBarItem(
                selected = selecting == "reward",
                onClick = {
                    navController.navigate("reward")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Rewards",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
            NavigationBarItem(
                selected = selecting == "order",
                onClick = {
                    navController.navigate("order")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "Calendar",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}