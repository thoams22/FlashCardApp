package com.example.flashcard.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flashcard.Action
import com.example.flashcard.ContentState
import com.example.flashcard.constants
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.screens.card.CardScreen
import com.example.flashcard.screens.learning.LearningScreen

fun NavGraphBuilder.learningComposable(
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, Int) -> Unit,
    navigateToLearningScreen:(Int)->Unit){
    composable(
        route = constants.LEARNING_SCREEN,
        arguments = listOf(navArgument(constants.LEARNING_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
        val folderId = navBackStackEntry.arguments!!.getInt(constants.LEARNING_ARGUMENT_KEY)
        cardViewModel.getSelectedFolder(folderId = folderId)
        val selectedFolder by cardViewModel.selectedFolder.collectAsState()

        cardViewModel.ContentState.value = ContentState.ANSWERING
        LearningScreen(selectedFolder = selectedFolder,
            navigateToListScreen = navigateToListScreen,
            cardViewModel = cardViewModel,
            navigateToLearningScreen = navigateToLearningScreen)
    }
}