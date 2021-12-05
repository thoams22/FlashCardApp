package com.example.flashcard.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.database.FolderWithCards
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    cards: RequestState<List<FolderWithCards>>,
    folder: Folder?,
    searchedCards: RequestState<List<Card>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (cardId: Int) -> Unit,
    onSwipeToDelete: (Action, Card)->Unit
){
    if (searchAppBarState == SearchAppBarState.TRIGGERED){
        if (searchedCards is RequestState.Success){

            HandleListContentSearched(folder= folder, cards = searchedCards.data, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
        }
    }else{
        if (cards is RequestState.Success && cards.data.isNotEmpty()){
            HandleListContent(
                folder = folder,
                cards = cards.data,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }else{
                EmptyContent()
            }

    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    cards: List<FolderWithCards>,
    folder: Folder?,
    onSwipeToDelete: (Action, Card)->Unit,
    navigateToTaskScreen: (cardId: Int) -> Unit

){
    if(cards.first().cards.isEmpty()){
        EmptyContent()
    }else{
        DisplayCard(folder = folder, cards = cards.first().cards, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContentSearched(
    folder: Folder?,
    cards: List<Card>,
    onSwipeToDelete: (Action, Card)->Unit,
    navigateToTaskScreen: (cardId: Int) -> Unit
){
    if(cards.isEmpty()){
        EmptyContent()
    }else{
        DisplayCard(folder = folder, cards = cards, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DisplayCard(folder: Folder?,cards: List<Card>, navigateToTaskScreen: (cardId: Int) -> Unit, onSwipeToDelete: (Action, Card)->Unit){

    LazyColumn{
        stickyHeader{
            val context = LocalContext.current
            var  imageBitmap by remember {
                mutableStateOf(latexImageBitmap(context, folder!!.folderName, alignment = LatexAlignment.Start))
            }
            Image(
                bitmap = imageBitmap,
                contentDescription = null
            )
            kotlin.runCatching { latexImageBitmap(context = context, folder!!.folderName,alignment = LatexAlignment.Start) }
                .getOrNull()?.let { bitmap ->
                    imageBitmap = bitmap
                }
        }
        items(
            items = cards,
            key = {
                    card-> card.cardId
            }){card ->
            val dismissState = rememberDismissState()

            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if(isDismissed && dismissDirection == DismissDirection.EndToStart){
                onSwipeToDelete(Action.DELETE_CARD,card)
            }
            val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(fraction = 0.3f) },
                background = { RedBackground(degrees = degrees)},
                dismissContent = {
                    CardItem(card = card, navigateToTaskScreen = navigateToTaskScreen)
                }
            )
        }
    }
}

@Composable
fun RedBackground(degrees: Float){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red)
        .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd){
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CardItem(
    card: Card,
    navigateToTaskScreen: (cardId: Int)->Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 2.dp,
        onClick = {
            navigateToTaskScreen(card.cardId)
        }
    ) {
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()) {
            Text(modifier = Modifier.fillMaxWidth(),
                text = card.question,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(modifier = Modifier.fillMaxWidth(),
                text = card.reponse,
                style= MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}