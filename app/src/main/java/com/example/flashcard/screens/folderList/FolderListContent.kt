package com.example.flashcard.screens.folderList

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.flashcard.*
import com.example.flashcard.R
import com.example.flashcard.database.Folder
import com.example.flashcard.screens.list.EmptyContent
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

@ExperimentalMaterialApi
@Composable
fun FolderListContent(
    folders: RequestState<List<Folder>>,
    searchedFolders: RequestState<List<Folder>>,
    searchAppBarState: SearchAppBarState,
    navigateToListScreen: (action: Action, Int) -> Unit,
    onSwipeToDelete: (Action, Folder)->Unit
){
    if (searchAppBarState == SearchAppBarState.TRIGGERED){
        if (searchedFolders is RequestState.Success){
            HandleListContent(
                folders = searchedFolders.data,
                navigateToListScreen = navigateToListScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
    }else{
        if (folders is RequestState.Success){
            HandleListContent(
                folders = folders.data,
                navigateToListScreen = navigateToListScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    folders: List<Folder>,
    onSwipeToDelete: (Action, Folder)->Unit,
    navigateToListScreen: (action: Action, Int) -> Unit
){
    if(folders.isEmpty()){
        EmptyContent()
    }else{
        DisplayFolder(
            folders = folders,
            navigateToListScreen = navigateToListScreen,
            onSwipeToDelete = onSwipeToDelete
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayFolder(
    folders: List<Folder>,
    navigateToListScreen: (Action, Int) -> Unit,
    onSwipeToDelete: (Action, Folder) -> Unit
){
    LazyColumn{
        items(
            items = folders,
            key = {
                    folder-> folder.folderId
            }){folder->
            val dismissState = rememberDismissState()

            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if(isDismissed && dismissDirection == DismissDirection.EndToStart){
                onSwipeToDelete(Action.DELETE_FOLDER, folder)
            }

            val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(fraction = 0.3f) },
                background = { RedBackground(degrees = degrees)},
                dismissContent = {FolderItem(folder = folder, navigateToListScreen = navigateToListScreen)}
            )
        }
    }
}


@Composable
fun RedBackground(degrees: Float){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red)
        .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd){
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun FolderItem(
    folder: Folder,
    navigateToListScreen: (Action, Int)->Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 2.dp,
        onClick = {
            navigateToListScreen(Action.NO_ACTION, folder.folderId)
        }
    ) {
        Column(modifier = Modifier
            .padding(12.dp), horizontalAlignment = Alignment.Start) {
            val context = LocalContext.current
            var imageBitmap by remember {
                mutableStateOf(latexImageBitmap(context, folder.folderName, alignment = LatexAlignment.Start))
            }
            Image(
                bitmap = imageBitmap,
                contentDescription = null
            )
            kotlin.runCatching { latexImageBitmap(context = context, folder.folderName,alignment = LatexAlignment.Start) }
                .getOrNull()?.let { bitmap ->
                    imageBitmap = bitmap
                }
        }
    }
}