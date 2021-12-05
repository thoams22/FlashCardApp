package com.example.flashcard.screens.folder

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import com.example.flashcard.Action
import com.example.flashcard.KeyboardState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@ExperimentalComposeUiApi
@Composable
fun FolderScreen(
    selectedFolder: Folder?,
    navigateToFolderListScreen: (Action)->Unit,
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, Int)-> Unit
){
    //val folderName: String by cardViewModel.folderName
    //val folderRelativePosition: String by cardViewModel.folderRelativePosition

    if (selectedFolder != null) {
        cardViewModel.ancientFolderId.value = selectedFolder.folderId
    }
    val context = LocalContext.current
    cardViewModel.KeyboardState.value = KeyboardState.DEFAULT

    Scaffold(
        topBar = {
            FolderAppBar(selectedFolder= selectedFolder,
                navigateToFolderListScreen = { action ->
                    if(action == Action.NO_ACTION){
                        navigateToFolderListScreen(action)
                    } else{
                        if (cardViewModel.validateFolderFields()){
                            navigateToFolderListScreen(action)
                        }else{
                            displayToast(context = context)
                        }
                    }
                }, navigateToListScreen = { action: Action, _: Int ->
                    if(action == Action.NO_ACTION){
                        navigateToListScreen(action, cardViewModel.ancientFolderId.value) }
                    else{
                    if (cardViewModel.validateFolderFields()){
                        navigateToListScreen(action, cardViewModel.folderid.value)
                    }else{
                        displayToast(context = context)
                    }
                }}
            )
        },
        content = {
            if(selectedFolder != null){
                FolderContent(
                    folderName = selectedFolder.folderName,
                    folderRelativePosition = selectedFolder.folderRelativePosition,
                    onFolderNameChange = {folderName: String, folderRelativePosition: String ->
                        cardViewModel.folderName.value = folderName
                        cardViewModel.folderRelativePosition.value = folderRelativePosition},
                    cardViewModel =cardViewModel
                )}
            else{
                FolderContent(
                    folderName = "",
                    folderRelativePosition = "",
                    onFolderNameChange = {folderName: String, folderRelativePosition: String ->
                        cardViewModel.folderName.value = folderName
                        cardViewModel.folderRelativePosition.value = folderRelativePosition},
                    cardViewModel =cardViewModel
                )
            }
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}