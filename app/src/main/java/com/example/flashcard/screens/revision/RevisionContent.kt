package com.example.flashcard.screens.revision

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.Action
import com.example.flashcard.RequestState
import com.example.flashcard.RevisionState
import com.example.flashcard.database.Card
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.database.Folder
import com.example.flashcard.database.FolderWithCards
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalMaterialApi
@Composable
fun RevisionContent(
    folderWithCards: RequestState<List<FolderWithCards>>,
    revisionState: RevisionState,
    cardViewModel: CardViewModel,
    navigateToListScreen: (Action, Int)->Unit,
    selectedFolder: Folder?,
    navigateToRevisionScreen: (Int)->Unit){
    if (folderWithCards is RequestState.Success && folderWithCards.data.isNotEmpty()){
        if(cardViewModel.cardList.isNotEmpty() && cardViewModel.cardList.first().cardId == -1){
            cardViewModel.cardList = folderWithCards.data.first().cards.shuffled().toMutableList()
        }
        else if(cardViewModel.cardList.isEmpty()){
            cardViewModel.cardList = folderWithCards.data.first().cards.shuffled().toMutableList()
        }
        if(cardViewModel.cardList.isNotEmpty()){
            val cards = cardViewModel.cardList
            AnsweringContent(
                cards = cards,
                revisionState = revisionState,
                cardViewModel = cardViewModel,
                navigateToRevisionScreen = navigateToRevisionScreen,
                navigateToListScreen = navigateToListScreen,
                selectedFolder = selectedFolder
            )
        }
    }
}

enum class SwipeResult{
    ACCEPTED, REJECTED
}

@ExperimentalMaterialApi
@Composable
fun AnsweringContent(cards: MutableList<Card>,
                     revisionState: RevisionState,
                     cardViewModel: CardViewModel,
                     navigateToListScreen: (Action, Int)->Unit,
                     selectedFolder: Folder?,
                     navigateToRevisionScreen: (Int)->Unit){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight - 150.dp
    val cardNbr = mutableListOf<Card>()
    val revState:RevisionState = if(revisionState == RevisionState.REPONSE){
        RevisionState.QUESTION
    }else{
        RevisionState.REPONSE
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.background(Color.LightGray)) {
            val listEmpty = remember { mutableStateOf(false) }
            cards.forEach { card ->
                DraggableCard(
                    revState = revState,
                    cardViewModel = cardViewModel,
                    item = card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    onSwiped = { result, swipedCard ->
                        if (result == SwipeResult.REJECTED) {
                            cards.remove(swipedCard)
                            cardNbr.add(swipedCard)
                            if (cards.isEmpty()) {
                                listEmpty.value = true
                            }
                        } else if (result == SwipeResult.ACCEPTED) {
                            cards.remove(swipedCard)
                            if (cards.isEmpty()) {
                                listEmpty.value = true
                            }
                        }
                    }
                ) {
                    CardContent(card, revisionState)
                }
            }
            if(listEmpty.value) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = cardHeight)
                ) {
                    IconButton(
                        onClick = {
                            cardViewModel.cardList = mutableListOf()
                            navigateToRevisionScreen(selectedFolder!!.folderId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            tint = Color.Black,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                                navigateToListScreen(Action.NO_ACTION, selectedFolder!!.folderId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            tint = Color.Black,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CardContent(card: Card, revisionState: RevisionState) {
    val context = LocalContext.current
    val value = if(revisionState == RevisionState.REPONSE){
        card.reponse
    }else{
        card.question
    }
    val textColor = MaterialTheme.colors.onSurface
    var imageBitmap by remember {
        mutableStateOf(latexImageBitmap(context, value, alignment = LatexAlignment.Start, color = textColor))
    }
    Image(
        modifier = Modifier
            .fillMaxSize(),
        bitmap = imageBitmap,
        contentDescription = null
    )
    runCatching {
        latexImageBitmap(context = context, value, alignment = LatexAlignment.Start, color = textColor) }
        .getOrNull()?.let { bitmap ->
            imageBitmap = bitmap
        }
}

@Composable
fun DraggableCard(
    cardViewModel: CardViewModel,
    revState: RevisionState,
    item: Card,
    modifier: Modifier = Modifier,
    onSwiped: (SwipeResult, Card) -> Unit,
    content: @Composable () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val swipeXLeft = -(screenWidth.value * 3.2).toFloat()
    val swipeXRight = (screenWidth.value * 3.2).toFloat()
    val swipeYTop = -1000f
    val swipeYBottom = 1000f
    val swipeX = remember { Animatable(0f) }
    val swipeY = remember { Animatable(0f) }
    swipeX.updateBounds(swipeXLeft, swipeXRight)
    swipeY.updateBounds(swipeYTop, swipeYBottom)
    if (abs(swipeX.value) < swipeXRight - 50f) {
        val rotationFraction = (swipeX.value / 60).coerceIn(-40f, 40f)
        Card(
            elevation = 16.dp,
            modifier = modifier
                .dragContent(
                    swipeX = swipeX,
                    swipeY = swipeY,
                    maxX = swipeXRight
                )
                .graphicsLayer(
                    translationX = swipeX.value,
                    translationY = swipeY.value,
                    rotationZ = rotationFraction,
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable { cardViewModel.revisionState.value = revState }
        ) {
            content()
            val alpha = (swipeX.value/900).coerceIn(-1f, 1f)
            if(alpha<-0.1f) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    tint = Color(1f, 0f, 0f, abs(alpha)),
                    contentDescription = null,
                    modifier = modifier
                )
            }
            else if(alpha>0.1f) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = Color(0f, 1f, 0f, abs(alpha)),
                    contentDescription = null,
                    modifier = modifier
                )
            }
        }
    } else {
        val swipeResult = if (swipeX.value > 0) SwipeResult.ACCEPTED else SwipeResult.REJECTED
        onSwiped(swipeResult, item)
    }
}

fun Modifier.dragContent(
    swipeX: Animatable<Float, AnimationVector1D>,
    swipeY: Animatable<Float, AnimationVector1D>,
    maxX: Float
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    pointerInput(Unit) {
        this.detectDragGestures(
            onDragCancel = {
                coroutineScope.apply {
                    launch { swipeX.animateTo(0f) }
                    launch { swipeY.animateTo(0f) }
                }
            },
            onDragEnd = {
                coroutineScope.apply {
                    if (abs(swipeX.targetValue) < abs(maxX) / 4) {
                        launch {
                            swipeX.animateTo(0f, tween(400))
                        }
                        launch {
                            swipeY.animateTo(0f, tween(400))
                        }
                    } else {
                        launch {
                            if (swipeX.targetValue > 0) {
                                swipeX.animateTo(maxX, tween(400))
                            } else {
                                swipeX.animateTo(-maxX, tween(400))
                            }
                        }
                    }
                }
            }
        ) { change, dragAmount ->
            change.consumePositionChange()
            coroutineScope.apply {
                launch { swipeX.animateTo(swipeX.targetValue + dragAmount.x) }
                launch { swipeY.animateTo(swipeY.targetValue + dragAmount.y) }
            }
        }
    }
}