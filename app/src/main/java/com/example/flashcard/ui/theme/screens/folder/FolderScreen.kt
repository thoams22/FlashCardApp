package com.example.flashcard.ui.theme.screens.folder

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.flashcard.Action
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@Composable
fun FolderScreen(
    selectedFolder: Folder?,
    navigateToFolderListScreen: (Action)->Unit,
    cardViewModel: CardViewModel
){

    val folderName: String by cardViewModel.folderName

    val context = LocalContext.current

    Scaffold(
        topBar = {
            FolderAppBar(selectedFolder= selectedFolder,
                navigateToFolderListScreen = { action ->
                    if(action == Action.NO_ACTION){
                        navigateToFolderListScreen(action)
                    }else{
                        if (cardViewModel.validateFolderFields()){
                            navigateToFolderListScreen(action)
                        }else{
                            displayToast(context = context)
                        }
                    }
                }
            )
        },
        content = {
                FolderContent(
                    folderName = folderName,
                    onFolderNameChange = {cardViewModel.folderName.value = it}
                )
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