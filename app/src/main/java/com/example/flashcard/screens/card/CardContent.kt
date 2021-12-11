package com.example.flashcard.screens.card

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.*
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.screens.folder.redBar
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

@Composable
fun CardContent(
    question: String,
    questionRelativePosition: String,
    onQuestionChange: (String, String)->Unit,
    reponse: String,
    reponseRelativePosition:String,
    onReponseChange: (String, String)->Unit,
    cardViewModel: CardViewModel
){
    val scrollState = rememberScrollState()
    val textColor = MaterialTheme.colors.onSurface
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .verticalScroll(state = scrollState), content = {

        var latexQ by remember { mutableStateOf(redBar + question)}
        var positionQ by remember { mutableStateOf(0) }
        var relativePositionQ by remember { mutableStateOf(questionRelativePosition) }
        var positionInRelativeQ by remember { mutableStateOf(0) }

        var latexR by remember { mutableStateOf(reponse)}
        var positionR by remember { mutableStateOf(0) }
        var relativePositionR by remember { mutableStateOf(reponseRelativePosition) }
        var positionInRelativeR by remember { mutableStateOf(0) }

        val context = LocalContext.current
        var focus by remember { mutableStateOf("Question")}

        var  imageBitmapQ by remember {
            mutableStateOf(latexImageBitmap(context, latexQ, alignment = LatexAlignment.Start, color = textColor))
        }
        var  imageBitmapR by remember {
            mutableStateOf(latexImageBitmap(context, latexR, alignment = LatexAlignment.Start, color = textColor))
        }
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp)
            .clickable {
                focus = "Question"
                latexR = latexR.replace(redBar, "")
                latexQ = latexQ.substring(0, positionQ) + redBar + latexQ.substring(positionQ)
            },
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapQ,
                contentDescription = null
            )
        }
        Card(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .heightIn(50.dp)
            .clickable {
                focus = "Reponse"
                latexQ = latexQ.replace(redBar, "")
                latexR = latexR.substring(0, positionR) + redBar + latexR.substring(positionR)
            },
        ) {
            Image(modifier = Modifier.background(MaterialTheme.colors.onSecondary),
                bitmap = imageBitmapR,
                contentDescription = null
            )
        }

        KeyboardAffiched(cardViewModel = cardViewModel, onKeyPressed = {text:String, taille:String->
            if(focus == "Question") {
                latexQ = latexQ.replace(redBar, "")

                if (text.contains("{")) {
                    latexQ = latexQ.substring(0, positionQ) + "${text.substring(0, taille[0].code - 48)}$redBar${text.substring(taille[0].code - 48)}" + latexQ.substring(positionQ)
                    positionQ += taille[0].code - 48
                }
                else {
                    latexQ = latexQ.substring(0, positionQ) + "$text$redBar" + latexQ.substring(positionQ)
                    positionQ += taille[0].code - 48
                }

                relativePositionQ = relativePositionQ.substring(0, positionInRelativeQ) + taille + relativePositionQ.substring(positionInRelativeQ)
                positionInRelativeQ += 1
                onReponseChange(latexQ.replace(redBar, ""), relativePositionQ)
            }
            else if(focus == "Reponse") {
                latexR = latexR.replace(redBar, "")

                if (text.contains("{")) {
                    latexR = latexR.substring(0, positionR) + "${text.substring(0, taille[0].code - 48)}$redBar${text.substring(taille[0].code - 48)}" + latexR.substring(positionR)
                    positionR += taille[0].code - 48
                }
                else {
                    latexR = latexR.substring(0, positionR) + "$text$redBar" + latexR.substring(positionR)
                    positionR += taille[0].code - 48
                }

                relativePositionR = relativePositionR.substring(0, positionInRelativeR) + taille + relativePositionR.substring(positionInRelativeR)
                positionInRelativeR += 1
                onReponseChange(latexR.replace(redBar, ""), relativePositionR)
            }
        },
            onActionPress = { action: String ->
                if(focus == "Question"){
                    HandleAction(
                        action = action, pos = positionQ, relPos = relativePositionQ, positionInRel = positionInRelativeQ, lat = latexQ,
                        result = {pos, relPos, posInRel, lat ->
                            positionQ=pos
                            relativePositionQ = relPos
                            positionInRelativeQ = posInRel
                            latexQ=lat
                        }
                    )
                }
                else if(focus == "Reponse"){
                    HandleAction(
                        action = action, pos = positionR, relPos = relativePositionR, positionInRel = positionInRelativeR, lat = latexR,
                        result = {pos, relPos, posInRel, lat ->
                            positionR=pos
                            relativePositionR = relPos
                            positionInRelativeR = posInRel
                            latexR=lat
                        }
                    )
                }
            }
        )
        kotlin.runCatching {
            latexImageBitmap(context = context, latexR,alignment = LatexAlignment.Start, color = textColor) }
            .getOrNull()?.let { bitmap ->
                imageBitmapR = bitmap
            }
        kotlin.runCatching {
            latexImageBitmap(context = context, latexQ,alignment = LatexAlignment.Start, color = textColor) }
            .getOrNull()?.let { bitmap ->
                imageBitmapQ = bitmap
            }
        onReponseChange(latexR.replace(redBar, ""), relativePositionR)
        onQuestionChange(latexQ.replace(redBar, ""), relativePositionQ)

    }, verticalArrangement = Arrangement.SpaceBetween)
}

@Composable
fun KeyboardAffiched(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)->Unit, onActionPress: (String)-> Unit){
    when (cardViewModel.KeyboardState.value){
        KeyboardState.DEFAULT -> {
            Keyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        KeyboardState.MATH -> {
            MathKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        KeyboardState.GREEK -> {
            GreekKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        KeyboardState.GREEKMAJ -> {
            GreekMajKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        KeyboardState.DEFAULTMAJ ->{
            MajKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
    }
}