package com.example.flashcard.screens.card

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.database.Card

@Composable
fun CardAppBar(
    selectedCard: Card?,
    folderId: Int,
    navigateToListScreen: (Action, Int)->Unit){
    if(selectedCard == null){
        NewCardAppBar(navigateToListScreen = navigateToListScreen,folderId=folderId)
    }else{
        ExistingCardAppBar(selectedCard = selectedCard,navigateToListScreen = navigateToListScreen)
    }
}

@Composable
fun NewCardAppBar(navigateToListScreen: (Action,Int)->Unit, folderId: Int){
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToListScreen, folderId=folderId)},
        title = {
        Text(text = stringResource(id = R.string.add_card))
    },
        actions = {
            AddAction(onBackClicked = navigateToListScreen, folderId=folderId)
        }
    )
}

@Composable
fun ExistingCardAppBar(
    selectedCard: Card,
    navigateToListScreen: (Action,Int)->Unit){
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToListScreen, folderId=selectedCard.folderId) },
        title = {
            Text(
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        actions = {
            ExistingCardAppBarAction(
                selectedCard=selectedCard,
                navigateToListScreen = navigateToListScreen
            )

        }
    )
}

@Composable
fun ExistingCardAppBarAction(
    selectedCard: Card,
    navigateToListScreen: (Action,Int)->Unit
){
    UpdateAction(onUpdateClicked = navigateToListScreen, folderId= selectedCard.folderId)
}

@Composable
fun AddAction(onBackClicked: (Action,Int)-> Unit, folderId: Int){
    IconButton(onClick = {onBackClicked(Action.ADD_CARD,folderId)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.add_card))
    }
}
@Composable
fun BackAction(onBackClicked: (Action,Int)-> Unit, folderId: Int){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION, folderId)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}
@Composable
fun CloseAction(onCloseClicked: (Action,Int)-> Unit, folderId: Int){
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION, folderId)}) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icons))
    }
}
@Composable
fun UpdateAction(onUpdateClicked: (Action,Int)-> Unit, folderId: Int){
    IconButton(onClick = {onUpdateClicked(Action.UPDATE_CARD,folderId)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.check_icon))
    }
}