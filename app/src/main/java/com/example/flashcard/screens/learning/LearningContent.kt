package com.example.flashcard.screens.learning

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flashcard.*
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.database.FolderWithCards
import com.example.flashcard.screens.folder.redBar
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LearningContent(
    folderWithCard: RequestState<List<FolderWithCards>>,
    PreResponse: String,
    contentState: ContentState,
    cardViewModel: CardViewModel,
    navigateToListScreen:(Action, Int)->Unit,
    selectedFolder: Folder?,
    navigateToLearningScreen: (Int)->Unit
){
    var notKnow by remember { mutableStateOf(0)}
    if (folderWithCard is RequestState.Success && folderWithCard.data.isNotEmpty()){
    if (cardViewModel.cardList.isNotEmpty() && cardViewModel.cardList.first().cardId == -1){
        cardViewModel.cardList = folderWithCard.data.first().cards.toMutableList()
    }
    if (cardViewModel.cardList.isNotEmpty()){
    val cards = cardViewModel.cardList

    when(contentState){
         ContentState.ANSWERING->{
                 HandleContent(cards = cards, PreResponse = PreResponse, cardViewModel = cardViewModel, onPreResponseChange = {preResponse, pospre->
                     cardViewModel.preReponse.value = preResponse
                 cardViewModel.preReponseRelativePosition.value = pospre})
    }
        ContentState.ISKNOW->{
            val card = cardViewModel.selectedCard.value
            if (card != null) {
                IsKnowContent(
                    PreResponse = cardViewModel.preReponse.value,
                    cardViewModel = cardViewModel,
                    card = card,
                    notKnow = notKnow,
                    onNotKnow = {not->
                        notKnow = not
                    }
                )
            }
        }
        else->{

        }
    }
    }else{
        RecapContent(navigateToListScreen = navigateToListScreen, navigateToLearningScreen = navigateToLearningScreen, selectedFolder = selectedFolder, notKnow = notKnow)
    }
}
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleContent(cards: MutableList<Card>,
                  PreResponse: String,
                  onPreResponseChange: (String, String) -> Unit,
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
    onPreResponseChange: (String, String)->Unit,
    cardViewModel: CardViewModel
){
    val textColor = MaterialTheme.colors.onSurface
    val context = LocalContext.current
    var latexR by remember { mutableStateOf(redBar + PreResponse)}
    val  imageBitmapQ by remember {
        mutableStateOf(latexImageBitmap(context, card.question, alignment = LatexAlignment.Start, color = textColor))
    }
    var imageBitmapR by remember {
        mutableStateOf(latexImageBitmap(context, latexR, alignment = LatexAlignment.Start, color = textColor))
    }
    var positionR by remember { mutableStateOf(0) }
    var relativePositionR by remember { mutableStateOf(card.reponseRelativePosition) }
    var positionInRelativeR by remember { mutableStateOf(0) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapQ,
                contentDescription = null
            )
        }

        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapR,
                contentDescription = null
            )
        }
        Button(onClick = { cardViewModel.ContentState.value = ContentState.ISKNOW} ,modifier = Modifier
            .align(Alignment.End)
            .padding(vertical = 16.dp)) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
        }

    KeyboardAffiched(cardViewModel = cardViewModel, onKeyPressed = {text:String, taille:String->
        latexR = latexR.replace(redBar, "")

        if (text.contains("{")) {
            latexR = latexR.substring(0, positionR) + "${text.substring(0, taille[0].code - 48)}$redBar${text.substring(taille[0].code - 48)}" + latexR.substring(positionR)
            positionR += taille[0].code - 48
        }
        else {
            latexR = latexR.substring(0, positionR) + "$text$redBar" + latexR.substring(positionR)
            positionR += taille[0].code - 48
        }

        relativePositionR = relativePositionR.substring(0, positionInRelativeR) + taille + relativePositionR.substring(positionInRelativeR)
        positionInRelativeR += 1
        onPreResponseChange(latexR.replace(redBar, ""), relativePositionR)},

        onActionPress = {action:String->
        HandleAction(action = action, pos = positionR, relPos = relativePositionR, positionInRel = positionInRelativeR, lat = latexR,
        result = {pos, relPos, posInRel, lat ->
            positionR=pos
            relativePositionR = relPos
            positionInRelativeR = posInRel
            latexR=lat
        }
    )})
        kotlin.runCatching {
            latexImageBitmap(context = context, latexR,alignment = LatexAlignment.Start, color = textColor) }
            .getOrNull()?.let { bitmap ->
                imageBitmapR = bitmap
            }
}}


@Composable
fun IsKnowContent(
    card: Card,
    PreResponse: String,
    cardViewModel: CardViewModel,
    notKnow: Int,
    onNotKnow: (Int) -> Unit
){
    val textColor = MaterialTheme.colors.onSurface
    val context = LocalContext.current
    val  imageBitmapQ by remember {
        mutableStateOf(latexImageBitmap(context, card.question, alignment = LatexAlignment.Start, color = textColor))
    }
    val  imageBitmapR by remember {
        mutableStateOf(latexImageBitmap(context, card.reponse, alignment = LatexAlignment.Start, color = textColor))
    }
    val  imageBitmapPR by remember {
        mutableStateOf(latexImageBitmap(context, PreResponse, alignment = LatexAlignment.Start, color = textColor))
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapQ,
                contentDescription = null
            )
        }
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapPR,
                contentDescription = null
            )
        }
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapR,
                contentDescription = null
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth(), content = {
            IconButton(
                onClick = {
                    onClickIsNotKnow(cardViewModel = cardViewModel, notKnow = notKnow, onNotKnow = onNotKnow)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    tint = Color.Red,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
            IconButton(
                onClick = {
                    onClickIsKnow(
                        cardViewModel = cardViewModel,
                        card = card
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = Color.Green,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
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
fun onClickIsNotKnow(cardViewModel: CardViewModel, notKnow:Int, onNotKnow:(Int)->Unit){
    cardViewModel.ContentState.value = ContentState.ANSWERING
    cardViewModel.getSelectedCard(-1)
    cardViewModel.preReponse.value = ""
    onNotKnow(notKnow+1)
}

@Composable
fun RecapContent(navigateToListScreen: (Action, Int) -> Unit, navigateToLearningScreen: (Int)->Unit, selectedFolder: Folder?, notKnow: Int){
    Column(modifier= Modifier.fillMaxSize(), Arrangement.SpaceAround) {
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) ,text = "You have been Wrong to $notKnow", textAlign = TextAlign.Center)
        Row(modifier = Modifier.fillMaxWidth(), content = {
            IconButton(
                    onClick = {
                        navigateToLearningScreen(selectedFolder!!.folderId)
                    },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.background)
            ) {
            Icon(
                imageVector = Icons.Default.RestartAlt,
                tint = Color.Black,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
            IconButton(
                onClick = {
                    navigateToListScreen(Action.NO_ACTION, selectedFolder!!.folderId)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    tint = Color.Black,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        })

    }
}
