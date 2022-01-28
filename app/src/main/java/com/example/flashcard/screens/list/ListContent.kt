package com.example.flashcard.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.Card
import com.example.flashcard.database.Folder
import com.example.flashcard.database.FolderWithCards
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap
import kotlin.math.roundToInt


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
            HandleListContentSearched(folder= folder, cards = searchedCards.data, onModifyClicked = navigateToTaskScreen, onDeleteClicked = onSwipeToDelete)
        }
    }else{
        if (cards is RequestState.Success){
            if(cards.data.isNotEmpty()){
                HandleListContent(
                    folder = folder,
                    cards = cards.data,
                    onModifyClicked = navigateToTaskScreen,
                    onDeleteClicked = onSwipeToDelete
                )}
        else{
                EmptyContent()
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    cards: List<FolderWithCards>,
    folder: Folder?,
    onDeleteClicked: (Action, Card)->Unit,
    onModifyClicked: (cardId: Int) -> Unit

){
    if(cards.first().cards.isEmpty()){
        EmptyContent()
    }else{
        DisplayCard(folder = folder, cards = cards.first().cards, onModifyClicked = onModifyClicked, onDeleteClicked = onDeleteClicked)
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContentSearched(
    folder: Folder?,
    cards: List<Card>,
    onDeleteClicked: (Action, Card)->Unit,
    onModifyClicked: (cardId: Int) -> Unit
){
    if(cards.isEmpty()){
        EmptyContent()
    }else{
        DisplayCard(folder = folder, cards = cards, onModifyClicked = onModifyClicked, onDeleteClicked = onDeleteClicked)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DisplayCard(folder: Folder?,cards: List<Card>, onModifyClicked: (cardId: Int) -> Unit, onDeleteClicked: (Action, Card)->Unit){
    val textColor = MaterialTheme.colors.onSurface
    LazyColumn{
        stickyHeader{
            Surface(modifier = Modifier.fillMaxWidth(), elevation = 1.dp){
            val context = LocalContext.current

            var  imageBitmap by remember {
                mutableStateOf(latexImageBitmap(context, folder!!.folderName, alignment = LatexAlignment.Start, color = textColor))
            }
            Image(modifier  =Modifier.padding(12.dp),
                bitmap = imageBitmap,
                contentDescription = null
            )
            kotlin.runCatching { latexImageBitmap(context = context, folder!!.folderName,alignment = LatexAlignment.Start, color = textColor) }
                .getOrNull()?.let { bitmap ->
                    imageBitmap = bitmap
                }}
        }
        items(
            items = cards,
            key = {
                    card-> card.cardId
            }){card ->

            var view by remember{mutableStateOf("Question")}
            var latex by remember{mutableStateOf(card.question)}

            val context = LocalContext.current
            var imageBitmap by remember {
                mutableStateOf(latexImageBitmap(context, latex, alignment = LatexAlignment.Start, color = textColor))
            }
            val squareSize = 150.dp

            val swipeableState = rememberSwipeableState(0)
            val sizePx = with(LocalDensity.current) {  squareSize.toPx() }
            val anchors = mapOf(-sizePx/2 to -1,0f to 0)

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                ) ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            onDeleteClicked(Action.DELETE_CARD, card)
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            tint = Color.Red,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Divider(modifier = Modifier.width(5.dp), thickness = 0.dp)
                    IconButton(
                        onClick = {
                            onModifyClicked(card.cardId)
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ModeEditOutline,
                            tint = Color.Green,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Divider(modifier = Modifier.width(5.dp), thickness = 0.dp)
                }
                Card(modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .align(Alignment.CenterStart),
                    onClick = {
                        if (view == "Reponse"){
                            view = "Question"
                            latex = card.question
                    }
                        else {
                            view = "Reponse"
                            latex = card.reponse
                        }
                    }
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        bitmap = imageBitmap,
                        contentDescription = null
                    )
                    runCatching { latexImageBitmap(context = context, latex, alignment = LatexAlignment.Start, color = textColor) }
                        .getOrNull()?.let { bitmap ->
                            imageBitmap = bitmap
                        }
                }
            }
        }
    }
}