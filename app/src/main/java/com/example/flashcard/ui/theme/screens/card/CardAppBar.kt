package com.example.flashcard.ui.theme.screens.card

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
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcard.Action
import com.example.flashcard.DisplayAlertDialog
import com.example.flashcard.R
import com.example.flashcard.database.Card

@Composable
fun CardAppBar(
    selectedCard: Card?,
    navigateToListScreen: (Action)->Unit){
    if(selectedCard == null){
        NewCardAppBar(navigateToListScreen = navigateToListScreen)
    }else{
        ExistingCardAppBar(selectedCard = selectedCard,navigateToListScreen = navigateToListScreen)
    }
}

@Composable
fun NewCardAppBar(navigateToListScreen: (Action)->Unit){
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToListScreen)},
        title = {
        Text(text = stringResource(id = R.string.add_card))
    },
        actions = {
            AddAction(onBackClicked = navigateToListScreen)
        }
    )
}

@Composable
fun ExistingCardAppBar(
    selectedCard: Card,
    navigateToListScreen: (Action)->Unit){
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToListScreen) },
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
    navigateToListScreen: (Action)->Unit
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
        onYesClicked = { navigateToListScreen(Action.DELETE)}
    )

    DeleteAction(onDeleteClicked = {openDialog = true})
    UpdateAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun AddAction(onBackClicked: (Action)-> Unit){
    IconButton(onClick = {onBackClicked(Action.ADD)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.add_card))
    }
}
@Composable
fun BackAction(onBackClicked: (Action)-> Unit){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}
@Composable
fun CloseAction(onCloseClicked: (Action)-> Unit){
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION)}) {
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
fun UpdateAction(onUpdateClicked: (Action)-> Unit){
    IconButton(onClick = {onUpdateClicked(Action.UPDATE)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.check_icon))
    }
}