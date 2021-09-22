package com.example.flashcard.screens.learning

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.ContentState
import com.example.flashcard.R
import com.example.flashcard.RequestState
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.database.FolderWithCards

@Composable
fun LearningContent(
    folderWithCard: RequestState<List<FolderWithCards>>,
    PreResponse: String,
    contentState: ContentState,
    cardViewModel: CardViewModel,
    navigateToListScreen:(Action, Int)->Unit,
    selectedFolder: Folder?,
    navigateToLearningScreen: (Int)->Unit
){if (folderWithCard is RequestState.Success && folderWithCard.data.isNotEmpty()){
    if (cardViewModel.cardList.isNotEmpty() && cardViewModel.cardList.first().cardId == -1){
        cardViewModel.cardList = folderWithCard.data.first().cards.toMutableList()
    }
    if (cardViewModel.cardList.isNotEmpty()){
    val cards = cardViewModel.cardList
    when(contentState){
         ContentState.ANSWERING->{
                 HandleContent(cards = cards, PreResponse = PreResponse, cardViewModel = cardViewModel, onPreResponseChange = { cardViewModel.preReponse.value = it })
    }
        ContentState.ISKNOW->{
            val card = cardViewModel.selectedCard.value
            if (card != null) {
                IsKnowContent(
                    PreResponse = cardViewModel.preReponse.value,
                    cardViewModel = cardViewModel,
                    card = card
                )
            }
        }
        else->{

        }
    }
    }else{
        RecapContent(navigateToListScreen = navigateToListScreen, navigateToLearningScreen = navigateToLearningScreen, selectedFolder = selectedFolder)
    }
}
}

@Composable
fun HandleContent(cards: MutableList<Card>,
                  PreResponse: String,
                  onPreResponseChange: (String) -> Unit,
                  cardViewModel: CardViewModel){
    if (cards.isNotEmpty()){
        var card: Card
        if(cardViewModel.selectedCard.value == null){
            card = cards.random()
            cardViewModel.getSelectedCard(card.cardId)
        }else{
            card = cardViewModel.selectedCard.value!!
            if(card !in cards){
                card = cards.random()
            }
        }
        Log.d("Learning", "$cards")
        Log.d("Learning", "$card")
        AnsweringContent(
        card = card,
        PreResponse = PreResponse,
        onPreResponseChange = onPreResponseChange,
        cardViewModel = cardViewModel
    )
    }
}


@Composable
fun AnsweringContent(
    card: Card,
    PreResponse: String,
    onPreResponseChange: (String)->Unit,
    cardViewModel: CardViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = card.question,
            Modifier
                .fillMaxWidth()
                .padding(16.dp), textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = PreResponse,
            onValueChange = { onPreResponseChange(it) },
            label = { Text(text = stringResource(id = R.string.preResponse))},
            textStyle = MaterialTheme.typography.body1
        )
        Button(onClick = { cardViewModel.ContentState.value = ContentState.ISKNOW} ,modifier = Modifier
            .align(Alignment.End)
            .padding(vertical = 16.dp)) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
        }
    }
}


@Composable
fun IsKnowContent(
    card: Card,
    PreResponse: String,
    cardViewModel: CardViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = card.question,
            Modifier
                .fillMaxWidth()
                .padding(16.dp), textAlign = TextAlign.Center
        )
        Text(text = PreResponse,
            Modifier
                .fillMaxWidth()
                .padding(16.dp), textAlign = TextAlign.Center
        )

        Text(text = card.reponse,
            Modifier
                .fillMaxWidth()
                .padding(16.dp), textAlign = TextAlign.Center
        )
        Row(modifier = Modifier
            .fillMaxWidth(), content = {
            Button(
                onClick = { onClickIsNotKnow(cardViewModel = cardViewModel) },
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Icon(imageVector = Icons.Filled.ThumbDownAlt, contentDescription = null,tint = Color.Red)
            }
            Button(onClick = { onClickIsKnow(
                cardViewModel = cardViewModel,
                card = card
            ) },modifier = Modifier
                .padding(vertical = 16.dp)) {
                Icon(imageVector = Icons.Filled.ThumbUpAlt, contentDescription = null,tint = Color.Green)
            }

        },horizontalArrangement = Arrangement.SpaceEvenly)
    }

}

fun onClickIsKnow(cardViewModel: CardViewModel, card: Card){
    cardViewModel.ContentState.value = ContentState.ANSWERING
    cardViewModel.getSelectedCard(-1)
    cardViewModel.preReponse.value = ""
    cardViewModel.cardList.remove(card)
}
fun onClickIsNotKnow(cardViewModel: CardViewModel){
    cardViewModel.ContentState.value = ContentState.ANSWERING
    cardViewModel.getSelectedCard(-1)
    cardViewModel.preReponse.value = ""
}

@Composable
fun RecapContent(navigateToListScreen: (Action, Int) -> Unit, navigateToLearningScreen: (Int)->Unit, selectedFolder: Folder?){
    val wrongNumber = 5
    Column(modifier= Modifier.fillMaxSize()) {
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) ,text = "You have been Wrong to $wrongNumber", textAlign = TextAlign.Center)
        Row(modifier = Modifier.fillMaxWidth(), content = {
            Button(onClick = {navigateToLearningScreen(selectedFolder!!.folderId)}) {
                Icon(imageVector = Icons.Filled.RestartAlt, contentDescription = null)
            }
            Button(onClick = {navigateToListScreen(Action.NO_ACTION, selectedFolder!!.folderId)}) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
            }
        })

    }
}