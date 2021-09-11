package com.example.flashcard.navigation.destination

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.flashcard.Action
import com.example.flashcard.constants
import com.example.flashcard.constants.FOLDER_ARGUMENT_KEY
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.ui.theme.screens.card.CardScreen
import com.example.flashcard.ui.theme.screens.folder.FolderScreen

fun NavGraphBuilder.folderComposable(
    cardViewModel: CardViewModel,
    navigateToFolderListScreen: (Action) -> Unit){
    composable(
        route = constants.FOLDER_SCREEN,
        arguments = listOf(navArgument(FOLDER_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){ navBackStackEntry ->
        val folderName = navBackStackEntry.arguments?.getString(FOLDER_ARGUMENT_KEY)

        if (folderName != null) {
            cardViewModel.getSelectedFolder(folderName = folderName)
        }
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        LaunchedEffect(key1 = selectedFolder){
            if (selectedFolder != null || folderName == "-1"){
                cardViewModel.updateSelectedFolder(selectedFolder=selectedFolder)
            }}

        FolderScreen(selectedFolder = selectedFolder,
            navigateToFolderListScreen = navigateToFolderListScreen,
            cardViewModel = cardViewModel)
    }
}