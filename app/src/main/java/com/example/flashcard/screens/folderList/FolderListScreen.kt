package com.example.flashcard.screens.folderList

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.flashcard.Action
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel

@ExperimentalMaterialApi
@Composable
fun FolderListScreen(
    navigateToListScreen: (Action, Int) -> Unit,
    navigateToFolderScreen: (Int)-> Unit,
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

    cardViewModel.handleDatabaseAction(action = action)

    Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
    FolderListAppBar(
        cardViewModel = cardViewModel,
        searchAppBarState = searchAppBarState,
        searchTextState = searchTextState,
        navigateToFolderScreen=navigateToFolderScreen
    )
},
    content = {
    FolderListContent(
        folders=folderList,
        navigateToListScreen = navigateToListScreen,
        searchedFolders = searchedFolder,
        searchAppBarState = searchAppBarState,
        onDeleteClicked = { action ->
            cardViewModel.action.value = action
        },
        onModifyClicked = navigateToFolderScreen,
        cardViewModel = cardViewModel
    )
}
)
}


