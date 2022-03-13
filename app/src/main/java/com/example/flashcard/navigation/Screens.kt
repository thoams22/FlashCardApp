package com.example.flashcard.navigation

import androidx.navigation.NavController
import com.example.flashcard.Action

class Screens(navController: NavController) {

    val list: (Action, Int) -> Unit = { action, folderId ->
        navController.navigate("list/$folderId/${action.name}")
    }

    val task: (Int) -> Unit = {cardId->
        navController.navigate("task/$cardId")
    }

    val folderList:(Action) -> Unit = { action ->
        navController.navigate("folderList/${action.name}")
    }

    val folder:(Int)-> Unit = {folderId ->
        navController.navigate("folder/$folderId")
    }

    val learning:(Int)->Unit = {folderId->
        navController.navigate("learning/$folderId")
    }

    val revision:(Int)->Unit = {folderId->
        navController.navigate("revision/$folderId")
    }
}