package com.example.flashcard.ui.theme.screens.folder

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.flashcard.Action
import com.example.flashcard.DisplayAlertDialog
import com.example.flashcard.R
import com.example.flashcard.database.Card
import com.example.flashcard.database.Folder


@Composable
fun FolderAppBar(
    selectedFolder: Folder?,
    navigateToFolderListScreen: (Action)->Unit){
    if(selectedFolder == null){
        NewFolderAppBar(navigateToFolderListScreen = navigateToFolderListScreen)
    }else{
        ExistingFolderAppBar(navigateToFolderListScreen = navigateToFolderListScreen, selectedFolder = selectedFolder)
    }
}

@Composable
fun NewFolderAppBar(navigateToFolderListScreen: (Action)->Unit){
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToFolderListScreen)},
        title = {
            Text(text = stringResource(id = R.string.add_folder))
        },
        actions = {
            AddAction(onBackClicked = navigateToFolderListScreen)
        }
    )
}

@Composable
fun ExistingFolderAppBar(
    selectedFolder: Folder,
    navigateToFolderListScreen: (Action)->Unit){
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToFolderListScreen) },
        title = {
            Text(
                text = "Rename : ${selectedFolder.folderName}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        actions = {
            ExistingCardAppBarAction(
                navigateToListScreen = navigateToFolderListScreen
            )

        }
    )
}

@Composable
fun ExistingCardAppBarAction(
    navigateToListScreen: (Action)->Unit
){
    UpdateAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun AddAction(onBackClicked: (Action)-> Unit){
    IconButton(onClick = {onBackClicked(Action.ADD_FOLDER)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.add_card))
    }
}
@Composable
fun BackAction(onBackClicked: (Action)-> Unit){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}
@Composable
fun CloseAction(onCloseClicked: (Action)-> Unit){
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icons))
    }
}
@Composable
fun DeleteAction(onDeleteClicked: ()->Unit){
    IconButton(onClick = {onDeleteClicked()}) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete_icon))
    }
}
@Composable
fun UpdateAction(onUpdateClicked: (Action)-> Unit){
    IconButton(onClick = {onUpdateClicked(Action.UPDATE_FOLDER)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.check_icon))
    }
}