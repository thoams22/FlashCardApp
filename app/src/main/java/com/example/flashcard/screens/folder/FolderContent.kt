package com.example.flashcard.screens.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flashcard.*
import com.example.flashcard.KeyboardState.*
import com.example.flashcard.R
import com.example.flashcard.database.CardViewModel


@Composable
fun FolderContent(
    folderName: String,
    onFolderNameChange: (String)->Unit,
    cardViewModel: CardViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    content = {
        OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = folderName,
        onValueChange = { onFolderNameChange(it) },
        label = { Text(text = stringResource(id = R.string.folderName)) },
        textStyle = MaterialTheme.typography.body1
    )
        KeyboardAffiched(cardViewModel = cardViewModel)
              },
    verticalArrangement = Arrangement.SpaceBetween)
}
@Composable
fun KeyboardAffiched(cardViewModel: CardViewModel){
    val keyboardState = cardViewModel.KeyboardState.value
    Scaffold {
        when (keyboardState){
            DEFAULT -> {
                Keyboard(cardViewModel)
            }
            MATH -> {
                MathKeyboard(cardViewModel)
            }
            DEFAULTEXPOSANT -> {
                MathKeyboard(cardViewModel)
            }
            DEFAULTMAJ ->{
                MajKeyboard(cardViewModel)
            }
            else->{

            }
        }}
}