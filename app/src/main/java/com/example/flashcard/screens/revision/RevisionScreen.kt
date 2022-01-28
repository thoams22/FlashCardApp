package com.example.flashcard.screens.revision

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.flashcard.Action
import com.example.flashcard.ContentState
import com.example.flashcard.RevisionState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@ExperimentalMaterialApi
@Composable
fun RevisionScreen(
    selectedFolder: Folder?,
    navigateToListScreen: (Action, Int) -> Unit,
    cardViewModel: CardViewModel,
    navigateToRevisionScreen: (Int) -> Unit
) {

    LaunchedEffect(key1 = true){
        cardViewModel.getFolderWithCards(selectedFolder!!.folderId)
        cardViewModel.contentState.value = ContentState.ANSWERING
    }
    val folderWithCards by cardViewModel.getFolderWithCards.collectAsState()
    val revisionState: RevisionState by cardViewModel.revisionState

    Scaffold(topBar = { RevisionAppBar(navigateToListScreen = navigateToListScreen, selectedFolder = selectedFolder)},
    content = { RevisionContent(
        folderWithCards = folderWithCards,
        revisionState = revisionState,
        cardViewModel = cardViewModel,
        navigateToListScreen = navigateToListScreen,
        selectedFolder = selectedFolder,
        navigateToRevisionScreen = navigateToRevisionScreen
    )
    })
}