package com.example.flashcard.ui.theme.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.R
import com.example.flashcard.SearchAppBarState
import com.example.flashcard.TrailingIconState
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder

@Composable
fun ListAppBar(
    cardViewModel: CardViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    navigateToFolderListScreen: (Action) -> Unit,
    selectedFolder: Folder?,
    navigateToFolderScreen:(Int)-> Unit
) {
    when(searchAppBarState){
        SearchAppBarState.CLOSED -> {
            if (selectedFolder?.folderName != null) {
                DefaultListAppBar(
                    onSearchClicked = {
                        cardViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                    },
                    navigateToFolderListScreen = navigateToFolderListScreen,
                    selectedFolder = selectedFolder,
                    navigateToFolderScreen = navigateToFolderScreen
                )
            }
        }

    else-> {
        SearchAppBar(
            text = searchTextState,
            onTextChange = {newText-> cardViewModel.searchTextState.value = newText},
            onCloseClicked = {cardViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                             cardViewModel.searchTextState.value = ""},
            onSearchClicked = {cardViewModel.searchDatabaseCard(searchQuery = it)}
        )
    }
    }

}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    navigateToFolderListScreen: (Action) -> Unit,
    selectedFolder: Folder,
    navigateToFolderScreen: (selectedFolder: Int) -> Unit
){
    TopAppBar(navigationIcon = {
        BackAction(
            onBackClicked = navigateToFolderListScreen
        )

    },title = {
        Text(text = stringResource(id = R.string.FolderName, selectedFolder.folderName))
    },

        actions={
                ListAppBarActions(onSearchClicked = onSearchClicked, navigateToFolderScreen = navigateToFolderScreen,
                    selectedFolder = selectedFolder.folderId)
                        },
        backgroundColor = MaterialTheme.colors.primary)
}

@Composable
fun ListAppBarActions(onSearchClicked: ()->Unit,navigateToFolderScreen: (selectedFolder: Int)->Unit, selectedFolder: Int){
    ModifyAction(
        onModifyClicked = navigateToFolderScreen,
        selectedFolder = selectedFolder
    )
    SearchAction(
        onSearchClicked = onSearchClicked
    )

}

@Composable
fun ModifyAction(onModifyClicked: (selectedFolder: Int)->Unit, selectedFolder: Int){
    IconButton(onClick = {onModifyClicked(selectedFolder)}
    ) {
        Icon(
            imageVector = Icons.Filled.ModeEdit,
            contentDescription = stringResource(id = R.string.search_actions)
        )
    }
}

@Composable
fun SearchAction(onSearchClicked: ()-> Unit){
    IconButton(onClick = {onSearchClicked()}
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_actions)
        )
    }

}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String)->Unit,
    onCloseClicked: ()-> Unit,
    onSearchClicked: (String) -> Unit
){
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
            onTextChange(it)
        },
            placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = stringResource(id = R.string.search_place_holder))},
        singleLine = true,
        leadingIcon = {
            IconButton(
                modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {}) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(id = R.string.serch_icons))
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                when(trailingIconState){
                    TrailingIconState.READY_TO_DELETE->{
                        onTextChange("")
                        trailingIconState = TrailingIconState.READY_TO_CLOSE
                    }
                    TrailingIconState.READY_TO_CLOSE->{
                        if(text.isNotEmpty()){
                            onTextChange("")
                        }else{
                            onCloseClicked()
                            trailingIconState= TrailingIconState.READY_TO_DELETE
                        }
                    }
                }
            }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close_icons))
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(text)
            })
        )
    }
}

@Composable
fun BackAction(onBackClicked: (Action)-> Unit){
    IconButton(onClick = {onBackClicked(Action.NO_ACTION)}) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_icon))
    }
}
