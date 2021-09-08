package com.example.flashcard.navigation.destination

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.flashcard.constants.LIST_ARGUMENT_KEY
import com.example.flashcard.constants.LIST_SCREEN
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.toAction
import com.example.flashcard.ui.theme.screens.ListScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(navigateToTaskScreen: (CardId: Int) -> Unit, cardviewModel: CardViewModel){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){ navBackStackEntry->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action){
            cardviewModel.action.value = action
        }

        ListScreen(navigateToTaskScreen = navigateToTaskScreen,
        cardViewModel = cardviewModel
        )
    }
}