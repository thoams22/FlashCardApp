package com.example.flashcard.ui.theme.screens.folder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcard.R
import com.example.flashcard.ui.theme.screens.card.CardContent

@Composable
fun FolderContent(
    folderName: String,
    onFolderNameChange: (String)->Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = folderName,
            onValueChange = { onFolderNameChange(it) },
            label = { Text(text = stringResource(id = R.string.folderName)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}
