package com.example.flashcard.navigation.destination

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.flashcard.constants
import com.example.flashcard.Action
import com.example.flashcard.constants.CARD_ARGUMENT_KEY
import com.example.flashcard.constants.LIST_ARGUMENT_KEY
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.ui.theme.screens.card.CardScreen

fun NavGraphBuilder.cardComposable(
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, String) -> Unit){
    composable(
        route = constants.CARD_SCREEN,
        arguments = listOf(navArgument(CARD_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        val cardId = navBackStackEntry.arguments!!.getInt(CARD_ARGUMENT_KEY)
        cardViewModel.getSelectedCard(cardId = cardId)
        val selectedCard by cardViewModel.selectedCard.collectAsState()
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        LaunchedEffect(key1 = selectedCard){
            if (selectedCard != null || cardId == -1){
            cardViewModel.updateSelectedCard(selectedCard=selectedCard, selectedFolder=selectedFolder!!.folderName)
        }}

    CardScreen(selectedCard = selectedCard,
        navigateToListScreen = navigateToListScreen,
        cardViewModel = cardViewModel, folderName= selectedFolder!!.folderName)
    }
}

