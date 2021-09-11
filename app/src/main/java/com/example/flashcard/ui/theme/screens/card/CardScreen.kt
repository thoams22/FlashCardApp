package com.example.flashcard.ui.theme.screens.card

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.flashcard.Action
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel

@Composable
fun CardScreen(
    selectedCard: Card?,
    navigateToListScreen: (action: Action, String)->Unit,
    cardViewModel: CardViewModel, folderName: String){

    val question: String by cardViewModel.question
    val reponse: String by cardViewModel.reponse

    val context = LocalContext.current
    Scaffold(
    topBar = {
        CardAppBar(
            folderName = folderName,
            selectedCard = selectedCard,
            navigateToListScreen = { action, string ->
                if(action == Action.NO_ACTION){
                    navigateToListScreen(action, string)
                }else{
                    if (cardViewModel.validateFields()){
                        navigateToListScreen(action, string)
                        }else{
                            displayToast(context = context)
                        }
                }
            }
        )
    },
    content = {
        CardContent(
        question = question,
        onQuestionChange = {cardViewModel.question.value = it},
        reponse = reponse,
        onReponseChange = {cardViewModel.reponse.value = it}
    )}
)
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}
