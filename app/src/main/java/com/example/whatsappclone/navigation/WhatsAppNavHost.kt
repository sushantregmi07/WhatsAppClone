package com.example.whatsappclone.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun WhatsAppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = PhoneAuthorizationRoute,
        modifier = modifier,
    ) {
        composable<PhoneAuthorizationRoute> {
            PlaceholderScreen("Phone Authorization")
        }

        composable<ChatsRoute> {
            PlaceholderScreen("Chats")
        }

        composable<ConversationRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ConversationRoute>()
            PlaceholderScreen("Conversation: ${route.contactId}")
        }
    }
}

@Composable
private fun PlaceholderScreen(label: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = label)
    }
}
