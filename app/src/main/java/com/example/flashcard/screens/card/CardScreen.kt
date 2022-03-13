package com.example.flashcard.screens.card

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.flashcard.Action
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@Composable
fun CardScreen(
    selectedCard: Card?,
    navigateToListScreen: (action: Action, Int)->Unit,
    cardViewModel: CardViewModel,
    selectedFolder: Folder?){

    val question: String by cardViewModel.question
    val reponse: String by cardViewModel.reponse
    val reponseRelativePosition: String by cardViewModel.reponseRelativePosition
    val questionRelativePosition: String by cardViewModel.questionRelativePosition

    val context = LocalContext.current

    Log.d("CardScreen", "$question")

    Scaffold(
    topBar = {
        CardAppBar(
            folderId = selectedFolder!!.folderId,
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
        if (selectedCard != null){
            CardContent(
                question = question,
                reponseRelativePosition = reponseRelativePosition,
                onQuestionChange = {question, questionRelativePosition ->
                    cardViewModel.question.value = question
                    cardViewModel.questionRelativePosition.value = questionRelativePosition},
                reponse = reponse,
                questionRelativePosition = questionRelativePosition,
                onReponseChange = {reponse, reponseRelativePosition->
                    cardViewModel.reponse.value = reponse
                    cardViewModel.reponseRelativePosition.value = reponseRelativePosition},
                cardViewModel = cardViewModel
            )
        }
        else{
            CardContent(
                question = "",
                reponseRelativePosition = "",
                onQuestionChange = {question, questionRelativePosition ->
                    cardViewModel.question.value = question
                    cardViewModel.questionRelativePosition.value = questionRelativePosition},
                reponse = "",
                questionRelativePosition = "",
                onReponseChange = {reponse, reponseRelativePosition->
                    cardViewModel.reponse.value = reponse
                    cardViewModel.reponseRelativePosition.value = reponseRelativePosition},
                cardViewModel = cardViewModel
            )
        }

    }
)
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}
