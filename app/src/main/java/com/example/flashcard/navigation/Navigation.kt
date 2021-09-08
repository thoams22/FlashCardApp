package com.example.flashcard.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.flashcard.constants.LIST_SCREEN
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.navigation.destination.listComposable
import com.example.flashcard.navigation.destination.taskComposable

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    cardviewModel: CardViewModel){
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(navController = navController,
        startDestination =  LIST_SCREEN
    ){
        listComposable(navigateToTaskScreen = screen.task, cardviewModel = cardviewModel)
        taskComposable(navigateToListScreen = screen.list, cardViewModel = cardviewModel)
    }
}