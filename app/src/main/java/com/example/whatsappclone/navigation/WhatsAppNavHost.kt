@file:Suppress("DEPRECATION")

package com.example.whatsappclone.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.feature.chats.ChatsScreen
import com.example.whatsappclone.feature.chats.ChatsViewModel
import com.example.whatsappclone.feature.conversation.ConversationScreen
import com.example.whatsappclone.feature.conversation.ConversationViewModel

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
            val viewModel: ChatsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ChatsScreen(
                uiState = uiState,
                onConversationClick = { conversationId ->
                    navController.navigate(ConversationRoute(contactId = conversationId))
                },
            )
        }

        composable<ConversationRoute> {
            val viewModel: ConversationViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ConversationScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onComposerTextChanged = viewModel::onComposerTextChanged,
                onSendClick = viewModel::sendMessage,
            )
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
