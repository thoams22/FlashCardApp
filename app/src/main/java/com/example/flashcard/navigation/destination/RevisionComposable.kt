package com.example.flashcard.navigation.destination

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcard.Action
import com.example.flashcard.Constants
import com.example.flashcard.ContentState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.screens.revision.RevisionScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.revisionComposable(
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, Int)->Unit,
    navigateToRevisionScreen: (Int)->Unit) {
    composable(route = Constants.REVISION_SCREEN,
    arguments = listOf(navArgument(Constants.REVISION_ARGUMENT_KEY){
        type = NavType.IntType
    })){
        navBackStackEntry ->
        val folderId = navBackStackEntry.arguments!!.getInt(Constants.REVISION_ARGUMENT_KEY)
        cardViewModel.getSelectedFolder(folderId = folderId)
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        cardViewModel.contentState.value = ContentState.ANSWERING
        RevisionScreen(
            selectedFolder = selectedFolder,
            navigateToListScreen = navigateToListScreen,
            navigateToRevisionScreen = navigateToRevisionScreen,
            cardViewModel = cardViewModel
        )
    }
}