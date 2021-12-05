package com.example.flashcard.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.screens.list.ListAppBar
import com.example.flashcard.screens.list.ListContent
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    cardViewModel: CardViewModel,
    navigateToFolderListScreen: (Action)-> Unit,
    selectedFolder: Folder?,
    navigateToFolderScreen: (Int)->Unit,
    navigateToLearningScreen: (Int) -> Unit
)
{    LaunchedEffect(key1 = true){
    if (selectedFolder != null) {
        cardViewModel.getFolderWithCards(selectedFolder.folderId)
        cardViewModel.getSelectedFolder(selectedFolder.folderId)
    }
    }

    val action by cardViewModel.action

    val selceF by cardViewModel.selectedFolder.collectAsState()
    val folderWithCardsList by cardViewModel.getFolderWithCards.collectAsState()
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
            searchTextState = searchTextState,
            navigateToFolderListScreen= navigateToFolderListScreen,
            selectedFolder = selceF,
            navigateToFolderScreen = navigateToFolderScreen,
            navigateToTaskScreen = navigateToTaskScreen
        )
    },
    content = {
        ListContent(
        cards=folderWithCardsList,
            folder=selceF,
        navigateToTaskScreen = navigateToTaskScreen,
        searchedCards = searchedCard,
        searchAppBarState = searchAppBarState,
        onSwipeToDelete = {
            action, card ->
            cardViewModel.action.value = action
            if (selectedFolder != null) {
                cardViewModel.updateSelectedCard(selectedCard = card, selectedFolderId=selectedFolder.folderId)
            }
        }
    )},
    floatingActionButton = {
        ListFab(onFabClicked = navigateToLearningScreen, selectedFolder = selectedFolder?.folderId)
    },
    )
}

@Composable
fun ListFab(
    onFabClicked: (CardId: Int) -> Unit,
    selectedFolder: Int?
){
    FloatingActionButton(onClick = {
        if (selectedFolder != null) {
            onFabClicked(selectedFolder)
        }
    }) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = stringResource(id = R.string.play_button))
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
        if (action == Action.DELETE_CARD){
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $cardTitle",
                    actionLabel = "UNDO_CARD"
                )
                undoDeletedCard(action = action,
                snackBarResult = snackBarResult,
                onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun undoDeletedCard(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action)-> Unit
){
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE_CARD){
        onUndoClicked(Action.UNDO_CARD)
    }
}