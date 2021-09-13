package com.example.flashcard.navigation

import androidx.navigation.NavController
import com.example.flashcard.Action
import com.example.flashcard.constants.FOLDER_LIST_SCREEN
import com.example.flashcard.constants.LIST_SCREEN

class Screens(navController: NavController) {

    val list: (Action, Int) -> Unit = { action, folderName ->
        navController.navigate("list/$folderName/${action.name}"){
            popUpTo(LIST_SCREEN) {inclusive=true}
        }
    }

    val task: (Int) -> Unit = {cardId->
        navController.navigate("task/$cardId")
    }

    val folderList:(Action) -> Unit = { action ->
        navController.navigate("folderList/${action.name}"){
            popUpTo(FOLDER_LIST_SCREEN) {inclusive=true}
        }
    }

    val folder:(Int)-> Unit = {folderName ->
        navController.navigate("folder/$folderName")
    }
}