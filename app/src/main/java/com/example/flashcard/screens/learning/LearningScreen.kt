package com.example.flashcard.screens.learning

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.flashcard.Action
import com.example.flashcard.ContentState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@Composable
fun LearningScreen(
    selectedFolder: Folder?,
    navigateToListScreen: (Action, Int) -> Unit,
    cardViewModel: CardViewModel,
    navigateToLearningScreen:(Int)->Unit
){
    LaunchedEffect(key1 = true){
        cardViewModel.getFolderWithCards(selectedFolder!!.folderId)
        cardViewModel.ContentState.value = ContentState.ANSWERING
    }
    val folderWithCard by cardViewModel.getFolderWithCards.collectAsState()
    val contentState: ContentState by cardViewModel.ContentState

        Scaffold(
            topBar = { LearningAppBar(
                selectedFolder = selectedFolder,
                navigateToListScreen = navigateToListScreen
            ) },
            content = {
                LearningContent(
                    folderWithCard = folderWithCard,
                    contentState = contentState,
                    PreResponse = cardViewModel.preReponse.value,
                    cardViewModel = cardViewModel,
                    navigateToListScreen = navigateToListScreen,
                    selectedFolder = selectedFolder,
                    navigateToLearningScreen=navigateToLearningScreen
                )
            }

        )
}

