package com.example.flashcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.flashcard.KeyboardState.*
import com.example.flashcard.database.CardViewModel

@Composable
fun Keyboard(cardViewModel: CardViewModel) {
        Column(
            content = {
                Row(content = {
                    button("a")
                    button("z")
                    button("e")
                    button("r")
                    button("t")
                    button("y")
                    button("u")
                    button("i")
                    button("o")
                    button("p")
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    button("q")
                    button("s")
                    button("d")
                    button("f")
                    button("g")
                    button("h")
                    button("j")
                    button("k")
                    button("l")
                    button("m")
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    TextButton(onClick = { cardViewModel.KeyboardState.value = DEFAULTMAJ }) {
                        Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                    }
                    button("w")
                    button("x")
                    button("c")
                    button("v")
                    button("b")
                    button("n")
                    button("'")
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                    }
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    TextButton(onClick = {  }) {
                        Icon(imageVector = Icons.Outlined.Pin, contentDescription = null)
                    }
                    button(",")
                    TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }) {
                        Icon(imageVector = Icons.Filled.Functions, contentDescription = null)
                    }
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = 25.dp))
                    }
                    button(".")
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                    }
                }, horizontalArrangement = Arrangement.SpaceEvenly)
            }, verticalArrangement = Arrangement.Bottom
        )
    }

@Composable
fun MathKeyboard(cardViewModel: CardViewModel) {
    Column(
        content = {
            Row(content = {
                button("\u2211")
                button("\u220F")
                button("\u222B")
                button("\u222C")
                button("\u222E")
                button("\u03D5")
                button("\u03C7")
                button("\u0394")
                button("\u03C3")
                button("\u03C4")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                button("\u03B1")
                button("\u03B2")
                button("\u03B3")
                button("\u03B4")
                button("\u03B5")
                button("\u03B6")
                button("\u03B7")
                button("\u03B8")
                button("\u03C6")
                button("\u03BA")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.KeyboardState.value = DEFAULTEXPOSANT }) {
                    Text(text = "\u00B9\u00B2\u00B3 ")
                }
                button("\u03BB")
                button("\u03BC")
                button("\u03C8")
                button("\u03BE")
                button("\u03C9")
                button("\u03C0")
                button("\u03C1")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Pin, contentDescription = null)
                }
                button(",")
                TextButton(onClick = { cardViewModel.KeyboardState.value = DEFAULT }) {
                    Text(text = "\u0041\u0042\u0043")
                }
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = 25.dp))
                }
                button(".")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun MajKeyboard(cardViewModel: CardViewModel) {
    Column(
        content = {
            Row(content = {
                button("A")
                button("Z")
                button("E")
                button("R")
                button("T")
                button("Y")
                button("U")
                button("I")
                button("O")
                button("P")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                button("Q")
                button("S")
                button("D")
                button("F")
                button("G")
                button("H")
                button("J")
                button("K")
                button("L")
                button("M")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                Button(onClick = { cardViewModel.KeyboardState.value = DEFAULT}) {
                    Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                }
                button("W")
                button("X")
                button("C")
                button("V")
                button("B")
                button("N")
                button("'")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = {  }) {
                    Icon(imageVector = Icons.Outlined.Pin, contentDescription = null)
                }
                button(",")
                TextButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Functions, contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = 25.dp))
                }
                button(".")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun ExposantKeyboard(cardViewModel: CardViewModel) {
    Column(
        content = {
            Row(content = {
                button("\u1D43")
                button("\u1D47")
                button("\u1D9C")
                button("\u1D43")
                button("\u1D57")
                button("\u02B8")
                button("\u1DB8")
                button("\u2071")
                button("\u1D58")
                button("\u1D56")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                button("\u0000")
                button("\u02E2")
                button("\u1D48")
                button("\u1DA0")
                button("\u1DA2")
                button("\u02B0")
                button("\u02B2")
                button("\u1D4F")
                button("\u02E1")
                button("\u1D50")
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                }
                button("\u02B7")
                button("\u1D58")
                button("\u1D9C")
                button("\u1D5B")
                button("\u1D47")
                button("\u207F")
                button("'")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = {  }) {
                    Icon(imageVector = Icons.Outlined.Pin, contentDescription = null)
                }
                button(",")
                TextButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Functions, contentDescription = null)
                }
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = 25.dp))
                }
                button(".")
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun button(text:String) {
    val width = LocalConfiguration.current.screenWidthDp / 10
    TextButton(onClick = { }, modifier = Modifier.size(width = width.dp, height = 35.dp)) {
        Text(text = text)
    }
}

