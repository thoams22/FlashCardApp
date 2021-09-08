package com.example.flashcard.ui.theme.screens.list

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.Card


@ExperimentalMaterialApi
@Composable
fun ListContent(
    cards: RequestState<List<Card>>,
    searchedCards: RequestState<List<Card>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (cardId: Int) -> Unit,
    onSwipeToDelete: (Action, Card)->Unit
){
    if (searchAppBarState == SearchAppBarState.TRIGGERED){
        if (searchedCards is RequestState.Success){
            HandleListContent(cards = searchedCards.data, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
        }
    }else{
        if (cards is RequestState.Success){
            HandleListContent(cards = cards.data, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    cards: List<Card>,
    onSwipeToDelete: (Action, Card)->Unit,
    navigateToTaskScreen: (cardId: Int) -> Unit
){
    if(cards.isEmpty()){
        EmptyContent()
    }else{
        DisplayCard(cards = cards, navigateToTaskScreen = navigateToTaskScreen, onSwipeToDelete = onSwipeToDelete)
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayCard(cards: List<Card>, navigateToTaskScreen: (cardId: Int) -> Unit, onSwipeToDelete: (Action, Card)->Unit){
    LazyColumn{
        items(
            items = cards,
            key = {
                    card-> card.cardId
            }){card->
            val dismissState = rememberDismissState()

            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if(isDismissed && dismissDirection == DismissDirection.EndToStart){
                onSwipeToDelete(Action.DELETE,card)
            }

            val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(fraction = 0.3f) },
                background = { RedBackground(degrees = degrees)},
                dismissContent = {CardItem(card = card, navigateToTaskScreen = navigateToTaskScreen)}
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

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPrev(){
    CardItem(card = Card(0,"srfsrff" ,"esfesf"), navigateToTaskScreen = {})
}