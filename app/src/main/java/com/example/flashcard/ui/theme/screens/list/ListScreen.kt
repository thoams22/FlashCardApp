package com.example.flashcard.ui.theme.screens

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.ui.theme.screens.list.ListAppBar
import com.example.flashcard.ui.theme.screens.list.ListContent
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    cardViewModel: CardViewModel
)
{
    LaunchedEffect(key1 = true){
        cardViewModel.readAll()
    }

    val action by cardViewModel.action

    val cardList by cardViewModel.allCards.collectAsState()
    val searchedCard by cardViewModel.searchCards.collectAsState()
    val searchAppBarState: SearchAppBarState by cardViewModel.searchAppBarState
    val searchTextState: String by cardViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { cardViewModel.handleDatabaseAction(action = action) },
        cardTitle = cardViewModel.question.value,
        action = action,
        onUndoClicked = { cardViewModel.action.value = it }
    )
Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
        ListAppBar(
            cardViewModel = cardViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState
        )
    },
    content = {ListContent(
        cards=cardList,
        navigateToTaskScreen = navigateToTaskScreen,
        searchedCards = searchedCard,
        searchAppBarState = searchAppBarState,
        onSwipeToDelete = {
            action, card ->
            cardViewModel.action.value = action
            cardViewModel.update(selectedCard = card)
        }
    )},
    floatingActionButton = {
        ListFab(onFabClicked = navigateToTaskScreen)
    })
}

@Composable
fun ListFab(
    onFabClicked: (CardId: Int) -> Unit
){
    FloatingActionButton(onClick = {
        onFabClicked(-1)
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button))
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: ()->Unit,
    onUndoClicked: (Action)->Unit,
    cardTitle: String,
    action: Action
){
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action){
        if (action != Action.NO_ACTION){
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $cardTitle",
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedCard(action = action,
                snackBarResult = snackBarResult,
                onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun setActionLabel(action: Action): String{
    return if(action.name == "DELETE"){
        "UNDO"
    }else{
        return "OK"
    }
}

private fun undoDeletedCard(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action)-> Unit
){
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE){
        onUndoClicked(Action.UNDO)
    }
}