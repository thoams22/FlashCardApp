package com.example.flashcard.screens.folder

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.handleAction
import com.example.flashcard.KeyboardAffiched
import com.example.flashcard.database.CardViewModel
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

const val lengthRedBar = 18
const val redBar = "\\textcolor{red}{|}"

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun FolderContent(
    folderName: String,
    folderRelativePosition: String,
    onFolderNameChange: (folderName:String, folderRelativePosition:String)->Unit,
    cardViewModel: CardViewModel
){
    val textColor = MaterialTheme.colors.onSurface
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), content = {

        var latex by remember { mutableStateOf(redBar+folderName)}
        var position by remember { mutableStateOf(0)}
        var relativePosition by remember { mutableStateOf(folderRelativePosition)}
        var positionInRelative by remember { mutableStateOf(0)}
        val context = LocalContext.current

        var  imageBitmap by remember {
            mutableStateOf(latexImageBitmap(context, latex, alignment = LatexAlignment.Start, color = textColor))
        }
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp),
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmap,
                contentDescription = null
            )
        }
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
            onActionPress = { action: String ->
                handleAction(
                    action = action, pos = position, relPos = relativePosition, positionInRel = positionInRelative, lat = latex,
                    result = {pos, relPos, posInRel, lat ->
                        position=pos
                    relativePosition = relPos
                    positionInRelative = posInRel
                    latex=lat}
                )
            }
        )
        kotlin.runCatching { latexImageBitmap(context = context, latex,alignment = LatexAlignment.Start, color = textColor) }
            .getOrNull()?.let { bitmap ->
                imageBitmap = bitmap
            }
        onFolderNameChange(latex.replace(redBar, ""), relativePosition)
    }, verticalArrangement = Arrangement.SpaceBetween)
}