package com.example.flashcard.ui.theme.screens.folderList

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun FolderListScreen(
    navigateToListScreen: (Action, String) -> Unit,
    navigateToFolderScreen: (String)-> Unit,
    cardViewModel: CardViewModel
)
{
    LaunchedEffect(key1 = true){
    cardViewModel.readAllFolder()
}
    val action by cardViewModel.action

    val folderList by cardViewModel.allFolders.collectAsState()
    val searchedFolder by cardViewModel.searchFolders.collectAsState()

    val searchAppBarState: SearchAppBarState by cardViewModel.searchAppBarState
    val searchTextState: String by cardViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
    scaffoldState = scaffoldState,
    handleDatabaseActions = { cardViewModel.handleDatabaseAction(action = action) },
    folderTitle = cardViewModel.folderName.value,
    action = action,
    onUndoClicked = { cardViewModel.action.value = it }
    )

    Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
    FolderListAppBar(
        cardViewModel = cardViewModel,
        searchAppBarState = searchAppBarState,
        searchTextState = searchTextState
    )
},
    content = {
    FolderListContent(
        folders=folderList,
        navigateToListScreen = navigateToListScreen,
        searchedFolders = searchedFolder,
        searchAppBarState = searchAppBarState,
        onSwipeToDelete = { action ->
            cardViewModel.action.value = action
        }
    )
},
    floatingActionButton = {
    FolderFab(onFabClicked = navigateToFolderScreen)
})
}

@Composable
fun FolderFab(
    onFabClicked: (folderName: String) -> Unit
){
    FloatingActionButton(onClick = {
        onFabClicked("-1")
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button)
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: ()->Unit,
    onUndoClicked: (Action)->Unit,
    folderTitle: String,
    action: Action
){
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action){
        if (action != Action.NO_ACTION){
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $folderTitle",
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedFolder(action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun setActionLabel(action: Action): String{
    return if(action.name == "DELETE_FOLDER"){
        "UNDO_FOLDER"
    }else{
        return "OK"
    }
}

private fun undoDeletedFolder(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action)-> Unit
){
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE_FOLDER){
        onUndoClicked(Action.UNDO_FOLDER)
    }
}