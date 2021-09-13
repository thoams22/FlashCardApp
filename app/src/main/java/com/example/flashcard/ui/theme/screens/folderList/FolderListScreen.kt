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
        searchTextState = searchTextState
    )
},
    content = {
    FolderListContent(
        folders=folderList,
        navigateToListScreen = navigateToListScreen,
        searchedFolders = searchedFolder,
        searchAppBarState = searchAppBarState,
        onSwipeToDelete = { action, folder ->

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
    onFabClicked: (folderName: Int) -> Unit
){
    FloatingActionButton(onClick = {
        onFabClicked(-1)
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button)
        )
    }
}

