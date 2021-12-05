package com.example.flashcard.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcard.Action
import com.example.flashcard.constants
import com.example.flashcard.constants.FOLDER_ARGUMENT_KEY
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.screens.folder.FolderScreen

@ExperimentalComposeUiApi
fun NavGraphBuilder.folderComposable(
    cardViewModel: CardViewModel,
    navigateToFolderListScreen: (Action) -> Unit,
    navigateToListScreen: (Action, Int)-> Unit){
    composable(
        route = constants.FOLDER_SCREEN,
        arguments = listOf(navArgument(FOLDER_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        val folderId = navBackStackEntry.arguments?.getInt(FOLDER_ARGUMENT_KEY)

        if (folderId != null) {
            cardViewModel.getSelectedFolder(folderId = folderId)
        }
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        LaunchedEffect(key1 = selectedFolder){
            if (selectedFolder != null || folderId == -1){
                cardViewModel.updateSelectedFolder(selectedFolder=selectedFolder)
            }}

        FolderScreen(
            selectedFolder = selectedFolder,
            navigateToFolderListScreen = navigateToFolderListScreen,
            cardViewModel = cardViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}