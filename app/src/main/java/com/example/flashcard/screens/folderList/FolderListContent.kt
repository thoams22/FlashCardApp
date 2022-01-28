
package com.example.flashcard.screens.folderList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.flashcard.Action
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.screens.list.EmptyContent
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun FolderListContent(
    folders: RequestState<List<Folder>>,
    searchedFolders: RequestState<List<Folder>>,
    searchAppBarState: SearchAppBarState,
    navigateToListScreen: (action: Action, Int) -> Unit,
    onDeleteClicked: (Action)->Unit,
    onModifyClicked: (Int) -> Unit,
    cardViewModel: CardViewModel
){
    if (searchAppBarState == SearchAppBarState.TRIGGERED){
        if (searchedFolders is RequestState.Success){
            HandleListContent(
                folders = searchedFolders.data,
                navigateToListScreen = navigateToListScreen,
                onDeleteClicked = onDeleteClicked,
                onModifyClicked = onModifyClicked,
                cardViewModel = cardViewModel
            )
        }
    }else{
        if (folders is RequestState.Success){
            HandleListContent(
                folders = folders.data,
                navigateToListScreen = navigateToListScreen,
                onDeleteClicked = onDeleteClicked,
                onModifyClicked = onModifyClicked,
                cardViewModel = cardViewModel
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    folders: List<Folder>,
    onDeleteClicked: (Action)->Unit,
    navigateToListScreen: (action: Action, Int) -> Unit,
    onModifyClicked: (Int) -> Unit,
    cardViewModel: CardViewModel
){
    if(folders.isEmpty()){
        EmptyContent()
    }else{
        DisplayFolder(
            folders = folders,
            navigateToListScreen = navigateToListScreen,
            onDeleteClicked = onDeleteClicked,
            onModifyClicked = onModifyClicked,
            cardViewModel = cardViewModel

        )
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayFolder(
    folders: List<Folder>,
    navigateToListScreen: (Action, Int) -> Unit,
    onDeleteClicked: (Action) -> Unit,
    onModifyClicked: (Int)->Unit,
    cardViewModel: CardViewModel
){
    val textColor = MaterialTheme.colors.onSurface
    LazyColumn{
        items(
            items = folders,
            key = {
                    folder-> folder.folderId
            }){folder->
            val context = LocalContext.current

            var imageBitmap by remember {
                mutableStateOf(latexImageBitmap(context, folder.folderName, alignment = LatexAlignment.Start, color = textColor))
            }
            val squareSize = 150.dp

            val swipeableState = rememberSwipeableState(0)
            val sizePx = with(LocalDensity.current) {  squareSize.toPx() }
            val anchors = mapOf(-sizePx/2 to -1,0f to 0)

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                ) ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    var openDialog by remember {
                        mutableStateOf(false)
                    }
                    IconButton(
                        onClick = {
                            cardViewModel.getFolderWithCards(folder.folderId)
                            openDialog = true
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        if(openDialog){
                            Dialog(onDismissRequest = { openDialog = false },
                                properties = DialogProperties(dismissOnClickOutside = true),
                                content = {
                                    Surface(modifier = Modifier.widthIn(180.dp).heightIn(180.dp), shape = RectangleShape, border = BorderStroke(1.dp, Color.LightGray)) {
                                        Column(modifier = Modifier.fillMaxWidth()){
                                            Text(modifier = Modifier.padding(10.dp), fontSize = 30.sp, text = "DELETE ?", textAlign = TextAlign.Left)
                                            Text(
                                                modifier = Modifier.padding(10.dp),
                                                text = "Are ou sure you want to delete this folder"
                                            )
                                            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                                OutlinedButton(modifier = Modifier.padding(10.dp),onClick = { openDialog = false }) {
                                                    Text(text = "Cancel")
                                                }
                                                Button(modifier = Modifier.padding(10.dp),onClick = {
                                                    onDeleteClicked(Action.DELETE_FOLDER)
                                                    openDialog = false}
                                                ) {
                                                    Text(text = "Delete")
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            tint = Color.Red,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Divider(modifier = Modifier.width(5.dp), thickness = 0.dp)
                    IconButton(
                        onClick = {
                            onModifyClicked(folder.folderId)
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ModeEditOutline,
                            tint = Color.Green,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Divider(modifier = Modifier.width(5.dp), thickness = 0.dp)
                }
                Card(modifier = Modifier
                    .offset {
                        IntOffset(swipeableState.offset.value.roundToInt(), 0)
                    }
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .align(Alignment.CenterStart),
                    onClick = { navigateToListScreen(Action.NO_ACTION, folder.folderId) }
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        bitmap = imageBitmap,
                        contentDescription = null
                    )
                    runCatching { latexImageBitmap(context = context, folder.folderName,alignment = LatexAlignment.Start, color = textColor) }
                        .getOrNull()?.let { bitmap ->
                            imageBitmap = bitmap
                        }
                }
            }
    }
}
}