package com.example.flashcard.navigation.destination

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcard.Action
import com.example.flashcard.ContentState
import com.example.flashcard.Constants
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.screens.learning.LearningScreen

fun NavGraphBuilder.learningComposable(
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, Int) -> Unit,
    navigateToLearningScreen:(Int)->Unit){
    composable(
        route = Constants.LEARNING_SCREEN,
        arguments = listOf(navArgument(Constants.LEARNING_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        val folderId = navBackStackEntry.arguments!!.getInt(Constants.LEARNING_ARGUMENT_KEY)
        cardViewModel.getSelectedFolder(folderId = folderId)
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        cardViewModel.ContentState.value = ContentState.ANSWERING
        LearningScreen(selectedFolder = selectedFolder,
            navigateToListScreen = navigateToListScreen,
            cardViewModel = cardViewModel,
            navigateToLearningScreen = navigateToLearningScreen)
    }
}