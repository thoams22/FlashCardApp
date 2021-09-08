package com.example.flashcard.ui.theme.screens.card

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

@Composable
fun CardContent(
    question: String,
    onQuestionChange: (String)->Unit,
    reponse: String,
    onReponseChange: (String)->Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = question,
            onValueChange = { onQuestionChange(it) },
            label = { Text(text = stringResource(id = R.string.question))},
            textStyle = MaterialTheme.typography.body1
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = reponse,
            onValueChange = { onReponseChange(it) },
            label = { Text( text = stringResource(id = R.string.reponse)) },
            textStyle = MaterialTheme.typography.body1
        )
    }
}
@Preview
@Composable
private fun A(){
    CardContent(
        question = "question",
        onQuestionChange = {},
        reponse = "reponse",
        onReponseChange = {}
    )
}