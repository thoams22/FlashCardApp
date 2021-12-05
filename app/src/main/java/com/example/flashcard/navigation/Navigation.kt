package com.example.flashcard.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.flashcard.constants.FOLDER_LIST_SCREEN
import com.example.flashcard.constants.LEARNING_SCREEN
import com.example.flashcard.constants.LIST_SCREEN
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.navigation.destination.*

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    cardviewModel: CardViewModel){
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(navController = navController,
        startDestination =  FOLDER_LIST_SCREEN
    ){
        listComposable(
            navigateToTaskScreen = screen.task,
            cardViewModel = cardviewModel,
            navigateToFolderListScreen = screen.folderList,
            navigateToFolderScreen = screen.folder,
            navigateToLearningScreen = screen.learning
        )
        cardComposable(
            navigateToListScreen = screen.list,
            cardViewModel = cardviewModel
        )
        folderlistComposable(
            navigateToListScreen = screen.list,
            cardViewModel = cardviewModel,
            navigateToFolderScreen = screen.folder
        )
        folderComposable(
            navigateToFolderListScreen = screen.folderList,
            cardViewModel = cardviewModel,
            navigateToListScreen = screen.list
        )
        learningComposable(
            navigateToListScreen = screen.list,
            cardViewModel = cardviewModel,
            navigateToLearningScreen = screen.learning
        )
    }
}