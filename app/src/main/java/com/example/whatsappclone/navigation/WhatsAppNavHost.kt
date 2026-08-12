@file:Suppress("DEPRECATION")

package com.example.whatsappclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.feature.authorization.PhoneAuthorizationScreen
import com.example.whatsappclone.feature.authorization.PhoneAuthorizationViewModel
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
            val viewModel: PhoneAuthorizationViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            PhoneAuthorizationScreen(
                uiState = uiState,
                onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
                onCountrySelected = viewModel::onCountrySelected,
                onCountryPickerToggle = viewModel::onCountryPickerToggle,
                onDoneClick = {
                    if (viewModel.submit()) {
                        navController.navigate(ChatsRoute) {
                            popUpTo(PhoneAuthorizationRoute) { inclusive = true }
                        }
                    }
                },
            )
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