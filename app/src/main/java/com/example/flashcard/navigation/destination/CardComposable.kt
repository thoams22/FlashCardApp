package com.example.flashcard.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.flashcard.constants
import com.example.flashcard.Action
import com.example.flashcard.constants.TASK_ARGUMENT_KEY
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.ui.theme.screens.card.CardScreen

fun NavGraphBuilder.taskComposable(
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action) -> Unit){
    composable(
        route = constants.TASK_SCREEN,
        arguments = listOf(navArgument(constants.TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        val cardId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        cardViewModel.getSelected(cardId = cardId)
        val selectedCard by cardViewModel.selectedCard.collectAsState()

        LaunchedEffect(key1 = selectedCard){
            if (selectedCard != null || cardId == -1){
            cardViewModel.update(selectedCard=selectedCard)
        }}

    CardScreen(selectedCard = selectedCard,
        navigateToListScreen = navigateToListScreen,
        cardViewModel = cardViewModel)
    }
}