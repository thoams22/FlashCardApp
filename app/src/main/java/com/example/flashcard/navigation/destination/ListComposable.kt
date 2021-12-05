package com.example.flashcard.navigation.destination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcard.Action
import com.example.flashcard.constants.LIST_ACTION_KEY
import com.example.flashcard.constants.LIST_ARGUMENT_KEY
import com.example.flashcard.constants.LIST_SCREEN
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.toAction
import com.example.flashcard.screens.ListScreen

@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (CardId: Int) -> Unit,
    cardViewModel: CardViewModel,
    navigateToFolderListScreen: (action: Action) -> Unit,
    navigateToFolderScreen: (Int)-> Unit,
    navigateToLearningScreen: (CardId: Int) -> Unit
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.IntType
        }, navArgument(LIST_ACTION_KEY){
            type = NavType.StringType
        })
    ){ navBackStackEntry->
        val action = navBackStackEntry.arguments!!.getString(LIST_ACTION_KEY).toAction()
        val folderId = navBackStackEntry.arguments?.getInt(LIST_ARGUMENT_KEY)
        cardViewModel.getSelectedFolder(folderId)
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        LaunchedEffect(key1 = action, key2 = folderId){
            cardViewModel.action.value = action
            cardViewModel.getSelectedFolder(folderId=folderId)
            cardViewModel.updateSelectedFolder(selectedFolder)
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            cardViewModel = cardViewModel,
            navigateToFolderListScreen = navigateToFolderListScreen,
            selectedFolder = selectedFolder,
            navigateToFolderScreen = navigateToFolderScreen,
            navigateToLearningScreen= navigateToLearningScreen
        )
    }
}
