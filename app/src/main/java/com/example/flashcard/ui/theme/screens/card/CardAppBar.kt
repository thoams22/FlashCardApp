package com.example.flashcard.ui.theme.screens.card

import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.flashcard.Action
import com.example.flashcard.DisplayAlertDialog
import com.example.flashcard.R
import com.example.flashcard.database.Card

@Composable
fun CardAppBar(
    selectedCard: Card?,
    folderName: String,
    navigateToListScreen: (Action, String)->Unit){
    if(selectedCard == null){
        NewCardAppBar(navigateToListScreen = navigateToListScreen,folderName=folderName)
    }else{
        ExistingCardAppBar(selectedCard = selectedCard,navigateToListScreen = navigateToListScreen)
    }
}

@Composable
fun NewCardAppBar(navigateToListScreen: (Action,String)->Unit, folderName: String){
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToListScreen, folderName=folderName)},
        title = {
        Text(text = stringResource(id = R.string.add_card))
    },
        actions = {
            AddAction(onBackClicked = navigateToListScreen, folderName=folderName)
        }
    )
}

@Composable
fun ExistingCardAppBar(
    selectedCard: Card,
    navigateToListScreen: (Action,String)->Unit){
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToListScreen, folderName=selectedCard.folderName) },
        title = {
            Text(
                text = selectedCard.question,
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
    navigateToListScreen: (Action,String)->Unit
){
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_card, selectedCard.question),
        message = stringResource(
            id = R.string.delete_card_confirmation,
            selectedCard.question
        ),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE_CARD, selectedCard.folderName)}
    )

    DeleteAction(onDeleteClicked = {openDialog = true})
    UpdateAction(onUpdateClicked = navigateToListScreen, folderName= selectedCard.folderName)
}

@Composable
fun AddAction(onBackClicked: (Action,String)-> Unit, folderName: String){
    IconButton(onClick = {onBackClicked(Action.ADD_CARD,folderName)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.add_card))
    }
}
@Composable
fun BackAction(onBackClicked: (Action,String)-> Unit, folderName: String){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION, folderName)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}

@Composable
fun CloseAction(onCloseClicked: (Action,String)-> Unit, folderName: String){
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION, folderName)}) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icons))
    }
}
@Composable
fun DeleteAction(onDeleteClicked: ()->Unit){
    IconButton(onClick = {onDeleteClicked()}) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete_icon))
    }
}
@Composable
fun UpdateAction(onUpdateClicked: (Action,String)-> Unit, folderName: String){
    IconButton(onClick = {onUpdateClicked(Action.UPDATE_CARD,folderName)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.check_icon))
    }
}