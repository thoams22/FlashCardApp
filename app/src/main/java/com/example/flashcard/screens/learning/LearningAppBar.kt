package com.example.flashcard.screens.learning

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.database.Folder

@Composable
fun LearningAppBar(navigateToListScreen: (Action, Int)->Unit, selectedFolder: Folder?){

    TopAppBar(navigationIcon = {
        BackAction(
            onBackClicked = navigateToListScreen,
            folderId = selectedFolder!!.folderId
        )
    },
    title = { Text(text = stringResource(
        id = R.string.FolderName,
        selectedFolder!!.folderName
    )) })
}

@Composable
fun BackAction(onBackClicked: (Action,Int)-> Unit, folderId: Int){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION, folderId)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}