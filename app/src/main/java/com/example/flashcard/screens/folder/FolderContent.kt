package com.example.flashcard.screens.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.*
import com.example.flashcard.KeyboardState.*
import com.example.flashcard.database.CardViewModel
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

const val lengthRedBar = 18
const val redBar = "\\textcolor{red}{|}"

@ExperimentalComposeUiApi
@Composable
fun FolderContent(
    folderName: String,
    folderRelativePosition: String,
    onFolderNameChange: (folderName:String, folderRelativePosition:String)->Unit,
    cardViewModel: CardViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), content = {

        var latex by remember { mutableStateOf(redBar+folderName)}
        var position by remember { mutableStateOf(0)}
        var relativePosition by remember { mutableStateOf(folderRelativePosition)}
        var positionInRelative by remember { mutableStateOf(0)}
        val context = LocalContext.current

        var  imageBitmap by remember {
            mutableStateOf(latexImageBitmap(context, latex, alignment = LatexAlignment.Start))
        }
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                bitmap = imageBitmap,
                contentDescription = null
            )

        KeyboardAffiched(cardViewModel = cardViewModel, onKeyPressed = {text:String, taille:String->
            latex = latex.replace(redBar, "")
            if(text.contains("{")){
                latex = latex.substring(0,position) + "${text.substring(0, taille[0].code-48)}$redBar${text.substring(taille[0].code-48)}" + latex.substring(position)
                position += taille[0].code-48
            }else{
                latex = latex.substring(0,position) + "$text$redBar" + latex.substring(position)
                position += taille[0].code-48
            }
            relativePosition = relativePosition.substring(0,positionInRelative) + taille + relativePosition.substring(positionInRelative)
            positionInRelative += 1
            onFolderNameChange(latex.replace(redBar, ""), relativePosition)
        },
            onPositionChanged = {
                if(it == "LEFT" && position > 0){
                    position -= relativePosition[positionInRelative-1].code -48
                    latex = latex.replace(redBar, "")
                    latex = latex.substring(0,position) + redBar + latex.substring(position)
                    positionInRelative -= 1
                }
                else if(it == "RIGHT" && position < latex.length - lengthRedBar){
                    position += relativePosition[positionInRelative].code -48
                    latex = latex.replace(redBar, "")
                    latex = latex.substring(0,position) + redBar + latex.substring(position)
                    positionInRelative += 1
                }
                else if(it == "DELETE" && position > 0){
                    // identify when symbol to delete check if all {}/[] are empty and delete them without letting {}/[] alone or just one
                    if(latex[position-1] == '{' || latex[position-1] == '['|| latex[position-1] == '}') {
                        if(relativePosition[positionInRelative-1].code > '3'.code){
                            if(relativePosition[positionInRelative].code == '1'.code && latex[position+ lengthRedBar] == '}'){
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+1)
                                relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(1)
                                positionInRelative -= 1
                            }
                            else if(relativePosition[positionInRelative].code == '3'.code && latex.substring(position + lengthRedBar, position + lengthRedBar+4) == "}_{}"){
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+4)
                                relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(2)
                                positionInRelative -= 1
                            }
                            else if(relativePosition[positionInRelative].code == '2'.code && latex.substring(position + lengthRedBar, position + lengthRedBar+3) == "]{}"){
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+3)
                                relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(2)
                                positionInRelative -= 1
                            }
                            // if {}/[] are not empty just move left
                            else{
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position)
                                positionInRelative -= 1
                            }
                        }
                        else if(relativePosition[positionInRelative-1].code == '2'.code){
                            if(relativePosition[positionInRelative].code == '1'.code && latex[position+ lengthRedBar] == '}'){
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+1)
                                relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(1)
                                positionInRelative -= 1
                            }
                            else if(relativePosition[positionInRelative].code == '3'.code && latex.substring(position + lengthRedBar, position + lengthRedBar+4) == "}_{}"){
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+4)
                                relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(2)
                                positionInRelative -= 1
                            }
                            // if {}/[] are not empty just move left
                            else{
                                position -= relativePosition[positionInRelative-1].code -48
                                latex = latex.replace(redBar, "")
                                latex = latex.substring(0,position) + redBar + latex.substring(position)
                                positionInRelative -= 1
                            }
                        }
                        // if not the last part of symbol just move left
                        else{
                            position -= relativePosition[positionInRelative-1].code -48
                            latex = latex.replace(redBar, "")
                            latex = latex.substring(0,position) + redBar + latex.substring(position)
                            positionInRelative -= 1
                        }
                    }
                    else{
                        position -= relativePosition[positionInRelative-1].code -48
                        latex = latex.replace(redBar, "")
                        latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48))
                        relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(0)
                        positionInRelative -= 1
                    }
                }
            }
        )
        kotlin.runCatching { latexImageBitmap(context = context, latex,alignment = LatexAlignment.Start) }
            .getOrNull()?.let { bitmap ->
                imageBitmap = bitmap
            }
        onFolderNameChange(latex.replace(redBar, ""), relativePosition)
    }, verticalArrangement = Arrangement.SpaceBetween)
}
@Composable
fun KeyboardAffiched(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)->Unit, onPositionChanged: (String)-> Unit){
    when (cardViewModel.KeyboardState.value){
            DEFAULT -> {
                Keyboard(cardViewModel, onKeyPressed, onPositionChanged)
            }
            MATH -> {
                MathKeyboard(cardViewModel, onKeyPressed, onPositionChanged)
            }
            GREEK -> {
                GreekKeyboard(cardViewModel, onKeyPressed, onPositionChanged)
            }
            GREEKMAJ -> {
                GreekMajKeyboard(cardViewModel, onKeyPressed, onPositionChanged)
            }
            DEFAULTMAJ ->{
                MajKeyboard(cardViewModel, onKeyPressed, onPositionChanged)
            }
        }
}