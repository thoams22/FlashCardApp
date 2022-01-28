package com.example.flashcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.KeyboardState.*
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.screens.folder.lengthRedBar
import com.example.flashcard.screens.folder.redBar
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

const val divider = 11.0
const val dividerAction = 8
const val heightSpaceBar = 25
const val divHeight = 15

@Composable
fun Keyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)-> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(modifier = Modifier.fillMaxWidth(),
            content = {
                Row(content = {
                    TextButton(onClick = { onKeyPressed("_{}^{}", "231") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square^{n}_{n}", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onKeyPressed("^{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square^{n}", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onKeyPressed("_{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square_{n}", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    ButtonCustom("a", onKeyPressed = onKeyPressed)
                    ButtonCustom("z", onKeyPressed = onKeyPressed)
                    ButtonCustom("e", onKeyPressed = onKeyPressed)
                    ButtonCustom("r", onKeyPressed = onKeyPressed)
                    ButtonCustom("t", onKeyPressed = onKeyPressed)
                    ButtonCustom("y", onKeyPressed = onKeyPressed)
                    ButtonCustom("u", onKeyPressed = onKeyPressed)
                    ButtonCustom("i", onKeyPressed = onKeyPressed)
                    ButtonCustom("o", onKeyPressed = onKeyPressed)
                    ButtonCustom("p", onKeyPressed = onKeyPressed)
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    ButtonCustom("q", onKeyPressed = onKeyPressed)
                    ButtonCustom("s", onKeyPressed = onKeyPressed)
                    ButtonCustom("d", onKeyPressed = onKeyPressed)
                    ButtonCustom("f", onKeyPressed = onKeyPressed)
                    ButtonCustom("g", onKeyPressed = onKeyPressed)
                    ButtonCustom("h", onKeyPressed = onKeyPressed)
                    ButtonCustom("j", onKeyPressed = onKeyPressed)
                    ButtonCustom("k", onKeyPressed = onKeyPressed)
                    ButtonCustom("l", onKeyPressed = onKeyPressed)
                    ButtonCustom("m", onKeyPressed = onKeyPressed)
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    TextButton(onClick = { cardViewModel.keyboardState.value = DEFAULTMAJ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                    }
                    ButtonCustom("w", onKeyPressed = onKeyPressed)
                    ButtonCustom("x", onKeyPressed = onKeyPressed)
                    ButtonCustom("c", onKeyPressed = onKeyPressed)
                    ButtonCustom("v", onKeyPressed = onKeyPressed)
                    ButtonCustom("b", onKeyPressed = onKeyPressed)
                    ButtonCustom("n", onKeyPressed = onKeyPressed)
                    ButtonCustom("'", onKeyPressed = onKeyPressed)
                    TextButton(onClick = { onPositionChanged("DELETE") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                    }
                }, horizontalArrangement = Arrangement.SpaceEvenly)
                Row(content = {
                    TextButton(onClick = { cardViewModel.keyboardState.value = CHIFFRE }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                    }
                    ButtonCustom(",", onKeyPressed = onKeyPressed)
                    TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                            , contentDescription = null)
                    }
                    OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                        Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                    }
                    ButtonCustom(".", onKeyPressed = onKeyPressed)
                    Button(onClick = { onKeyPressed("\\\\ ", "3")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                    }
                }, horizontalArrangement = Arrangement.SpaceEvenly)
            }, verticalArrangement = Arrangement.Bottom
        )
    }


@Composable
fun ChiffreKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)-> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(modifier = Modifier.fillMaxWidth(),
        content = {
            Row(content = {
                TextButton(onClick = { onKeyPressed("_{}^{}", "231") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square^{n}_{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onKeyPressed("^{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square^{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onKeyPressed("_{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square_{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("0", onKeyPressed = onKeyPressed)
                ButtonCustom("1", onKeyPressed = onKeyPressed)
                ButtonCustom("2", onKeyPressed = onKeyPressed)
                ButtonCustom("3", onKeyPressed = onKeyPressed)
                ButtonCustom("4", onKeyPressed = onKeyPressed)
                ButtonCustom("5", onKeyPressed = onKeyPressed)
                ButtonCustom("6", onKeyPressed = onKeyPressed)
                ButtonCustom("7", onKeyPressed = onKeyPressed)
                ButtonCustom("8", onKeyPressed = onKeyPressed)
                ButtonCustom("9", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\AE ", onKeyPressed = onKeyPressed)
                ButtonCustom("# ", onKeyPressed = onKeyPressed)
                ButtonCustom("€ ", onKeyPressed = onKeyPressed)
                ButtonCustom("_ ", onKeyPressed = onKeyPressed)
                ButtonCustom(" ", onKeyPressed = onKeyPressed)
                ButtonCustom("-", onKeyPressed = onKeyPressed)
                ButtonCustom("+", onKeyPressed = onKeyPressed)
                ButtonCustom("(", onKeyPressed = onKeyPressed)
                ButtonCustom(")", onKeyPressed = onKeyPressed)
                ButtonCustom("/ ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = MATHMAJ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{=<>}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom("*", onKeyPressed = onKeyPressed)
                ButtonCustom("\"", onKeyPressed = onKeyPressed, taille = "1")
                ButtonCustom("\'", onKeyPressed = onKeyPressed, taille = "1")
                ButtonCustom(":", onKeyPressed = onKeyPressed)
                ButtonCustom(";", onKeyPressed = onKeyPressed)
                ButtonCustom("!", onKeyPressed = onKeyPressed)
                ButtonCustom("?", onKeyPressed = onKeyPressed)
                TextButton(onClick = { onPositionChanged("DELETE") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = DEFAULT }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{ABC}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(".", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}


@Composable
fun MathMajKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)-> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(modifier = Modifier.fillMaxWidth(),
        content = {
            Row(content = {
                TextButton(onClick = { onKeyPressed("_{}^{}", "231") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square^{n}_{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onKeyPressed("^{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square^{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onKeyPressed("_{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\square_{n}", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("~", onKeyPressed = onKeyPressed)
                ButtonCustom("`", onKeyPressed = onKeyPressed)
                ButtonCustom("|", onKeyPressed = onKeyPressed)
                ButtonCustom("´", onKeyPressed = onKeyPressed)
                ButtonCustom("\\frac{n}{n}", onKeyPressed = onKeyPressed, keysend = "\\frac{}{}", taille = "621")
                ButtonCustom("*", onKeyPressed = onKeyPressed)
                ButtonCustom("/", onKeyPressed = onKeyPressed)
                ButtonCustom("\\  ", onKeyPressed = onKeyPressed, taille = "3")
                ButtonCustom("\\angle ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\P ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("£", onKeyPressed = onKeyPressed)
                ButtonCustom("$", onKeyPressed = onKeyPressed)
                ButtonCustom("µ", onKeyPressed = onKeyPressed)
                ButtonCustom("°", onKeyPressed = onKeyPressed)
                ButtonCustom("^ ", onKeyPressed = onKeyPressed)
                ButtonCustom("=", onKeyPressed = onKeyPressed)
                ButtonCustom("", onKeyPressed = onKeyPressed)
                ButtonCustom("\\nparallel ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\eth ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\perp ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = CHIFFRE }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom("%", onKeyPressed = onKeyPressed)
                ButtonCustom("\\bigotimes ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\lim ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\bowtie ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\parallel ", onKeyPressed = onKeyPressed)
                ButtonCustom("", onKeyPressed = onKeyPressed)
                ButtonCustom("\\hbar ", onKeyPressed = onKeyPressed)
                TextButton(onClick = { onPositionChanged("DELETE") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = DEFAULT }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{ABC}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom("<", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(">", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun MathKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String) -> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(
        content = {
            Row(content = {
            TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                    , contentDescription = null)
            }
            TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                    , contentDescription = null)
            }
        }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\sum^{n}_{n}", onKeyPressed = onKeyPressed, keysend = "\\sum^{}_{}", taille = "631")
                ButtonCustom("\\int^{n}_{n}", onKeyPressed = onKeyPressed, keysend = "\\int^{}_{}", taille = "631")
                ButtonCustom("\\oint^{n}_{n}", onKeyPressed = onKeyPressed, keysend = "\\oint^{}_{}", taille = "731")
                ButtonCustom("\\pm ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\equiv ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\approx ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\simeq ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\sim ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\neq ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\to ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\gets ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\partial ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\nabla ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\infty ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\imath ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\jmath ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\sin ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\cos ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\tan ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\arcsin ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\alpha}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom("\\arccos ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\arctan ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\cot ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\csc ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\log ", onKeyPressed = onKeyPressed)
                ButtonCustom("\\sqrt[n]{n} ", onKeyPressed = onKeyPressed, keysend = "\\sqrt[]{}", taille = "621")
                ButtonCustom("\\sqrt{n} ", onKeyPressed = onKeyPressed, keysend = "\\sqrt{}", taille = "61")
                TextButton(onClick = { onPositionChanged("DELETE") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = DEFAULT }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{ABC}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2")}) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(".", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun GreekMajKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String) -> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\Alpha", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Beta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Gamma", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Delta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Epsilon", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Zeta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Eta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Theta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Iota", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Kappa", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\Lambda", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Mu", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Nu", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Xi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Omicron", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Pi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Rho", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Sigma", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Tau", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Upsilon", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                Button(onClick = { cardViewModel.keyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("\\Phi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Chi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Psi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Omega", onKeyPressed = onKeyPressed)
                ButtonCustom("\\exists", onKeyPressed = onKeyPressed)
                ButtonCustom("\\nexists", onKeyPressed = onKeyPressed)
                ButtonCustom("\\forall", onKeyPressed = onKeyPressed)
                TextButton(onClick = { onPositionChanged("DELETE") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(".", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun GreekKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String) -> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\alpha", onKeyPressed = onKeyPressed)
                ButtonCustom("\\beta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\gamma", onKeyPressed = onKeyPressed)
                ButtonCustom("\\delta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\epsilon", onKeyPressed = onKeyPressed)
                ButtonCustom("\\zeta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\eta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\theta", onKeyPressed = onKeyPressed)
                ButtonCustom("\\iota", onKeyPressed = onKeyPressed)
                ButtonCustom("\\kappa", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\lambda", onKeyPressed = onKeyPressed)
                ButtonCustom("\\mu", onKeyPressed = onKeyPressed)
                ButtonCustom("\\nu", onKeyPressed = onKeyPressed)
                ButtonCustom("\\xi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\omicron", onKeyPressed = onKeyPressed)
                ButtonCustom("\\pi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\rho", onKeyPressed = onKeyPressed)
                ButtonCustom("\\sigma", onKeyPressed = onKeyPressed)
                ButtonCustom("\\tau", onKeyPressed = onKeyPressed)
                ButtonCustom("\\upsilon", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = GREEKMAJ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("\\phi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\chi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\psi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\omega", onKeyPressed = onKeyPressed)
                ButtonCustom("\\in", onKeyPressed = onKeyPressed)
                ButtonCustom("\\notin", onKeyPressed = onKeyPressed)
                ButtonCustom("\\ni", onKeyPressed = onKeyPressed)
                TextButton(onClick = { onPositionChanged("DELETE")}, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = CHIFFRE }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(".", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun MajKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String) -> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface

    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("A", onKeyPressed = onKeyPressed)
                ButtonCustom("Z", onKeyPressed = onKeyPressed)
                ButtonCustom("E", onKeyPressed = onKeyPressed)
                ButtonCustom("R", onKeyPressed = onKeyPressed)
                ButtonCustom("T", onKeyPressed = onKeyPressed)
                ButtonCustom("Y", onKeyPressed = onKeyPressed)
                ButtonCustom("U", onKeyPressed = onKeyPressed)
                ButtonCustom("I", onKeyPressed = onKeyPressed)
                ButtonCustom("O", onKeyPressed = onKeyPressed)
                ButtonCustom("P", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("Q", onKeyPressed = onKeyPressed)
                ButtonCustom("S", onKeyPressed = onKeyPressed)
                ButtonCustom("D", onKeyPressed = onKeyPressed)
                ButtonCustom("F", onKeyPressed = onKeyPressed)
                ButtonCustom("G", onKeyPressed = onKeyPressed)
                ButtonCustom("H", onKeyPressed = onKeyPressed)
                ButtonCustom("J", onKeyPressed = onKeyPressed)
                ButtonCustom("K", onKeyPressed = onKeyPressed)
                ButtonCustom("L", onKeyPressed = onKeyPressed)
                ButtonCustom("M", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                Button(onClick = { cardViewModel.keyboardState.value = DEFAULT},Modifier.size(width = (LocalConfiguration.current.screenWidthDp/ dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("W", onKeyPressed = onKeyPressed)
                ButtonCustom("X", onKeyPressed = onKeyPressed)
                ButtonCustom("C", onKeyPressed = onKeyPressed)
                ButtonCustom("V", onKeyPressed = onKeyPressed)
                ButtonCustom("B", onKeyPressed = onKeyPressed)
                ButtonCustom("N", onKeyPressed = onKeyPressed)
                ButtonCustom("?", onKeyPressed = onKeyPressed)
                TextButton(onClick = { onPositionChanged("DELETE") },Modifier.size(width = (LocalConfiguration.current.screenWidthDp/ dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.keyboardState.value = CHIFFRE }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.keyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start, color = textColor)
                        , contentDescription = null)
                }
                OutlinedButton(onClick = { onKeyPressed("\\ ", "2") }) {
                    Icon(imageVector = Icons.Filled.SpaceBar, contentDescription = null, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/4).dp, height = heightSpaceBar.dp))
                }
                ButtonCustom(".", onKeyPressed = onKeyPressed)
                Button(onClick = { onKeyPressed("\\\\ ", "3") }, Modifier.size(width = (LocalConfiguration.current.screenWidthDp/ dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.KeyboardReturn, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
        }, verticalArrangement = Arrangement.Bottom
    )
}

@Composable
fun ButtonCustom(latex:String, keysend:String = latex, div:Double = divider, onKeyPressed: (text:String, taille:String)->Unit, taille:String = latex.length.toString()) {
    val width = LocalConfiguration.current.screenWidthDp / div
    val context = LocalContext.current
    val height = LocalConfiguration.current.screenHeightDp / divHeight
    val textColor = MaterialTheme.colors.onSurface
    val imageBitmap by remember {
        mutableStateOf(latexImageBitmap(context, latex, alignment = LatexAlignment.Start, color = textColor))
    }
    TextButton(onClick = { onKeyPressed(keysend, taille)}, modifier = Modifier.size(width = width.dp, height = height.dp)) {
        Image(bitmap = imageBitmap, contentDescription = null)
    }
}

@Composable
fun KeyboardAffiched(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)->Unit, onActionPress: (String)-> Unit){
    when (cardViewModel.keyboardState.value){
        DEFAULT -> {
            Keyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        MATH -> {
            MathKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        GREEK -> {
            GreekKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        GREEKMAJ -> {
            GreekMajKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        DEFAULTMAJ ->{
            MajKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        MATHMAJ ->{
            MathMajKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
        CHIFFRE ->{
            ChiffreKeyboard(cardViewModel, onKeyPressed, onActionPress)
        }
    }
}

fun handleAction(action:String, pos:Int, relPos:String, positionInRel:Int, lat: String, result:(pos:Int, relPos:String, posInRel:Int, lat:String)->Unit){
    var position = pos
    var relativePosition = relPos
    var positionInRelative = positionInRel
    var latex = lat

    if(action == "LEFT" && position > 0){
        position -= relativePosition[positionInRelative-1].code -48
        latex = latex.replace(redBar, "")
        latex = latex.substring(0,position) + redBar + latex.substring(position)
        positionInRelative -= 1
    }
    else if(action == "RIGHT" && position < latex.length - lengthRedBar){
        position += relativePosition[positionInRelative].code -48
        latex = latex.replace(redBar, "")
        latex = latex.substring(0,position) + redBar + latex.substring(position)
        positionInRelative += 1
    }
    else if(action == "DELETE" && position > 0){
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
                else if(relativePosition[positionInRelative].code == '3'.code && latex.substring(position + lengthRedBar, position + lengthRedBar +4) == "}_{}"){
                    position -= relativePosition[positionInRelative-1].code -48
                    latex = latex.replace(redBar, "")
                    latex = latex.substring(0,position) + redBar + latex.substring(position+(relativePosition[positionInRelative-1].code -48)+4)
                    relativePosition = relativePosition.substring(0,positionInRelative-1) + relativePosition.substring(positionInRelative).drop(2)
                    positionInRelative -= 1
                }
                else if(relativePosition[positionInRelative].code == '2'.code && latex.substring(position + lengthRedBar, position + lengthRedBar +3) == "]{}"){
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
                else if(relativePosition[positionInRelative].code == '3'.code && latex.substring(position + lengthRedBar, position + lengthRedBar +4) == "}_{}"){
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
    result(position, relativePosition, positionInRelative, latex)
}