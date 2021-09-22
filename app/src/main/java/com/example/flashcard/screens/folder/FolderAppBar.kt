package com.example.flashcard.screens.folder

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.database.Folder


@Composable
fun FolderAppBar(
    selectedFolder: Folder?,
    navigateToFolderListScreen: (Action) -> Unit,
    navigateToListScreen: (Action, Int) -> Unit
){
    if(selectedFolder == null){
        NewFolderAppBar(navigateToFolderListScreen = navigateToFolderListScreen)
    }else{
        ExistingFolderAppBar(selectedFolder = selectedFolder, navigateToListScreen=navigateToListScreen,navigateToFolderListScreen=navigateToFolderListScreen)
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
    navigateToListScreen: (Action, Int)->Unit,
    navigateToFolderListScreen: (Action)->Unit){
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigateToListScreen, folderId = selectedFolder.folderId) },
        title = {
            Text(
                text = "Rename : ${selectedFolder.folderName}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        actions = {
            ExistingCardAppBarAction(
                navigateToFolderListScreen = navigateToFolderListScreen
            )
        }
    )
}

@Composable
fun ExistingCardAppBarAction(
    navigateToFolderListScreen: (Action) -> kotlin.Unit,
){
    UpdateAction(onUpdateClicked = navigateToFolderListScreen)
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
fun CloseAction(onCloseClicked: (Action, Int)-> Unit, folderId: Int){
    IconButton(onClick = {onCloseClicked(Action.NO_ACTION, folderId)}) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icons))
    }
}
@Composable
fun UpdateAction(onUpdateClicked: (Action)-> Unit){
    IconButton(onClick = {onUpdateClicked(Action.UPDATE_FOLDER)}) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.check_icon))
    }
}