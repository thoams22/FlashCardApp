package com.example.flashcard.navigation.destination

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.flashcard.Action
import com.example.flashcard.constants.FOLDER_LIST_ARGUMENT_KEY
import com.example.flashcard.constants.FOLDER_LIST_SCREEN
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.toAction
import com.example.flashcard.ui.theme.screens.folderList.FolderListScreen


@ExperimentalMaterialApi
fun NavGraphBuilder.folderlistComposable(
    navigateToListScreen: (action: Action,Int) -> Unit,
    navigateToFolderScreen: (Int) -> Unit,
    cardViewModel: CardViewModel
){
    composable(
        route = FOLDER_LIST_SCREEN,
        arguments = listOf(navArgument(FOLDER_LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(FOLDER_LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action){
            cardViewModel.action.value = action
        }

        FolderListScreen(navigateToListScreen = navigateToListScreen,
            navigateToFolderScreen = navigateToFolderScreen,
        cardViewModel=cardViewModel)
    }
}