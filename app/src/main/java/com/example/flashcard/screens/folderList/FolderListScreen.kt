package com.example.flashcard.screens.folderList

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
            cardViewModel.getSelectedFolder(folder.folderId)
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
            contentDescription = stringResource(id = com.example.flashcard.R.string.add_button)
        )
    }
}

