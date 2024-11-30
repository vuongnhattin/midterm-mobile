package com.example.midtermmobile.ui.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.midtermmobile.mock.MockUserInfo
import com.example.midtermmobile.model.UserInfo
import com.example.midtermmobile.viewmodel.UserInfoViewModel

class ProfileComponent {
    var icon: ImageVector = Icons.Rounded.AccountCircle
    var header: String = ""
    var content: String = ""
    var onSave: (String) -> Unit = {}
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userInfoViewModel: UserInfoViewModel,
    navController: NavController
) {
    val userInfo = userInfoViewModel.info.collectAsState().value

    var isDialogVisible by remember { mutableStateOf(false) }
    var dialogHeader by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }
    var onSaveAction: (String) -> Unit by remember { mutableStateOf({}) }

    val profileComponents: List<ProfileComponent> = listOf(
        ProfileComponent().apply {
            icon = Icons.Rounded.AccountCircle
            header = "Full Name"
            content = userInfo.fullName
            onSave = userInfoViewModel::setFullName
        },
        ProfileComponent().apply {
            icon = Icons.Rounded.Phone
            header = "Phone number"
            content = userInfo.phone
            onSave = userInfoViewModel::setPhone
        },
        ProfileComponent().apply {
            icon = Icons.Rounded.Email
            header = "Email"
            content = userInfo.email
            onSave = userInfoViewModel::setEmail
        },
        ProfileComponent().apply {
            icon = Icons.Rounded.Home
            header = "Address"
            content = userInfo.address
            onSave = userInfoViewModel::setAddress
        },
    )

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
                text = "Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center) // Ensure title stays centered
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column() {
            LazyColumn {
                items(profileComponents) { component ->
                    ProfileItem(
                        icon = component.icon,
                        header = component.header,
                        content = component.content,
                        onSave = component.onSave
                    ) { header, content, onSave ->
                        dialogHeader = header
                        dialogContent = content
                        onSaveAction = onSave
                        isDialogVisible = true
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

        if (isDialogVisible) {
            EditDialog(
                header = dialogHeader,
                initialContent = dialogContent,
                onDismiss = { isDialogVisible = false },
                onSave = { newContent ->
                    onSaveAction(newContent)
                    isDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    header: String,
    content: String,
    onSave: (String) -> Unit,
    onEditClick: (header: String, content: String, onSave: (String) -> Unit) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(
                    text = header,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.width(200.dp)
                )
            }
        }
        IconButton(
            onClick = {
                onEditClick(header, content) { newContent ->
                    onSave(newContent)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun EditDialog(
    header: String,
    initialContent: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(initialContent) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(header) },
        text = {
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = { Text("Edit $header") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(textFieldValue) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val userInfoViewModel: UserInfoViewModel = viewModel()
    userInfoViewModel.setUser(MockUserInfo.userInfo)
    ProfileScreen(userInfoViewModel = viewModel(), navController = rememberNavController())
}