package com.example.flashcard.screens.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    cardViewModel: CardViewModel,
    navigateToFolderListScreen: (Action)-> Unit,
    selectedFolder: Folder,
    navigateToLearningScreen: (Int) -> Unit,
    navigateToRevisionScreen: (Int) -> Unit
)
{
    LaunchedEffect(key1 = selectedFolder){
        cardViewModel.getSelectedFolder(selectedFolder.folderId)
        cardViewModel.getFolderWithCards(selectedFolder.folderId)
    }
    val action by cardViewModel.action

    val folderWithCardsList by cardViewModel.getFolderWithCards.collectAsState()
    val selceF by cardViewModel.selectedFolder.collectAsState()
    val searchedCard by cardViewModel.searchCards.collectAsState()
    val searchAppBarState: SearchAppBarState by cardViewModel.searchAppBarState
    val searchTextState: String by cardViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    cardViewModel.handleDatabaseAction(action = action)

Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
        ListAppBar(
            cardViewModel = cardViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState,
            navigateToFolderListScreen= navigateToFolderListScreen,
            selectedFolder = selceF,
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
        },cardViewModel = cardViewModel
    )},
    floatingActionButton = {
        ListFab(onLearnClicked = navigateToLearningScreen, onRevisionClicked = navigateToRevisionScreen, selectedFolder = selectedFolder.folderId)
    },
    )
}

@Composable
fun ListFab(
    onLearnClicked: (CardId: Int) -> Unit,
    onRevisionClicked: (CardId: Int) -> Unit,
    selectedFolder: Int?
){
    var openDialog by remember {
        mutableStateOf(false)
    }
    FloatingActionButton(onClick = {
        openDialog = true

    }) {if(openDialog){
        Dialog(onDismissRequest = { openDialog = false },
            properties = DialogProperties(dismissOnClickOutside = true),
            content = {
                Surface(modifier = Modifier
                    .widthIn(180.dp)
                    .heightIn(180.dp), shape = RectangleShape, border = BorderStroke(1.dp, Color.LightGray)) {
                Column(modifier = Modifier.fillMaxWidth()){
                    Text(modifier = Modifier.padding(10.dp), fontSize = 30.sp, text = "Type of learning", textAlign = TextAlign.Left)
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Revision help to familiarize with the cards\nLearning is a methods to verify that you now the cards"
                )
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(modifier = Modifier.padding(10.dp),onClick = { onLearnClicked(selectedFolder!!) }) {
                        Text(text = "Learning")
                    }
                    OutlinedButton(modifier = Modifier.padding(10.dp),onClick = {onRevisionClicked(selectedFolder!!)}) {
                        Text(text = "Revision")
                    }
                }
                }
                }
            }
        )
    }
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = stringResource(id = R.string.play_button))
    }
}
