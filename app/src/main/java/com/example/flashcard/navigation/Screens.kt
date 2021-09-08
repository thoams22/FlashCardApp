package com.example.flashcard.navigation

import androidx.navigation.NavController
import com.example.flashcard.Action
import com.example.flashcard.constants.LIST_SCREEN

class Screens(navController: NavController) {
    val list:(Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN) {inclusive=true}
        }
    }
    val task: (Int) -> Unit = {cardId-> navController.navigate("task/$cardId")}
}