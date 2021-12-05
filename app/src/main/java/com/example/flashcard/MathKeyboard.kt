package com.example.flashcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flashcard.KeyboardState.*
import com.example.flashcard.database.CardViewModel
import com.wakaztahir.composejlatex.LatexAlignment
import com.wakaztahir.composejlatex.latexImageBitmap

const val divider = 11.0
const val dividerAction = 8
const val height = 45
const val heightSpaceBar = 25

@Composable
fun Keyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String)-> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(),
            content = {
                Row(content = {
                    TextButton(onClick = { onKeyPressed("_{}^{}", "231") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square^{n}_{n}", alignment = LatexAlignment.Start)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onKeyPressed("^{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square^{n}", alignment = LatexAlignment.Start)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onKeyPressed("_{}", "21") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\square_{n}", alignment = LatexAlignment.Start)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start)
                            , contentDescription = null)
                    }
                    TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 5).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start)
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
                    TextButton(onClick = { cardViewModel.KeyboardState.value = DEFAULTMAJ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
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
                    TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                    }
                    ButtonCustom(",", onKeyPressed = onKeyPressed)
                    TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                        Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start)
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
fun MathKeyboard(cardViewModel: CardViewModel, onKeyPressed: (text:String, taille:String) -> Unit, onPositionChanged:(String)->Unit) {
    val context = LocalContext.current
    Column(
        content = {
            Row(content = {
            TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start)
                    , contentDescription = null)
            }
            TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start)
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
                ButtonCustom("\\rightarrow ", onKeyPressed = onKeyPressed)
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                ButtonCustom("\\leftarrow ", onKeyPressed = onKeyPressed)
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
                TextButton(onClick = { cardViewModel.KeyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\alpha}\$", alignment = LatexAlignment.Start)
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
                TextButton(onClick = { cardViewModel.KeyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.KeyboardState.value = DEFAULT }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{ABC}\$", alignment = LatexAlignment.Start)
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
    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start)
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
                Button(onClick = { cardViewModel.KeyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("\\Phi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Chi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Psi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\Omega", onKeyPressed = onKeyPressed)
                ButtonCustom("\\exists", onKeyPressed = onKeyPressed)
                ButtonCustom("\\nexists", onKeyPressed = onKeyPressed)
                ButtonCustom("\\forall", onKeyPressed = onKeyPressed)
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.KeyboardState.value = GREEK }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start)
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
    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start)
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
                TextButton(onClick = { cardViewModel.KeyboardState.value = GREEKMAJ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("\\phi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\chi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\psi", onKeyPressed = onKeyPressed)
                ButtonCustom("\\omega", onKeyPressed = onKeyPressed)
                ButtonCustom("\\in", onKeyPressed = onKeyPressed)
                ButtonCustom("\\notin", onKeyPressed = onKeyPressed)
                ButtonCustom("\\ni", onKeyPressed = onKeyPressed)
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start)
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
    Column(
        content = {
            Row(content = {
                TextButton(onClick = { onPositionChanged("LEFT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\leftarrow}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                TextButton(onClick = { onPositionChanged("RIGHT") }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / 2).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\rightarrow}\$", alignment = LatexAlignment.Start)
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
                Button(onClick = { cardViewModel.KeyboardState.value = DEFAULT},Modifier.size(width = (LocalConfiguration.current.screenWidthDp/ dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                }
                ButtonCustom("W", onKeyPressed = onKeyPressed)
                ButtonCustom("X", onKeyPressed = onKeyPressed)
                ButtonCustom("C", onKeyPressed = onKeyPressed)
                ButtonCustom("V", onKeyPressed = onKeyPressed)
                ButtonCustom("B", onKeyPressed = onKeyPressed)
                ButtonCustom("N", onKeyPressed = onKeyPressed)
                ButtonCustom("?", onKeyPressed = onKeyPressed)
                TextButton(onClick = { /*TODO*/ },Modifier.size(width = (LocalConfiguration.current.screenWidthDp/ dividerAction).dp, height = height.dp)) {
                    Icon(imageVector = Icons.Outlined.Backspace, contentDescription = null)
                }
            }, horizontalArrangement = Arrangement.SpaceEvenly)
            Row(content = {
                TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{123}\$", alignment = LatexAlignment.Start)
                        , contentDescription = null)
                }
                ButtonCustom(",", onKeyPressed = onKeyPressed)
                TextButton(onClick = { cardViewModel.KeyboardState.value = MATH }, modifier = Modifier.size(width = (LocalConfiguration.current.screenWidthDp / dividerAction).dp, height = height.dp)) {
                    Image(bitmap = latexImageBitmap(context, "\\Large\$\\mathbf{\\Sigma}\$", alignment = LatexAlignment.Start)
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
fun ButtonCustom(latex:String, keysend:String = latex, div:Double = divider, onKeyPressed: (text:String, taille:String)->Unit, taille:String = latex.length.toString(), heigt: Int = height) {
    val width = LocalConfiguration.current.screenWidthDp / div
    val context = LocalContext.current

    val imageBitmap by remember {
        mutableStateOf(latexImageBitmap(context, latex, alignment = LatexAlignment.Start))
    }
    TextButton(onClick = { onKeyPressed(keysend, taille)}, modifier = Modifier.size(width = width.dp, height = heigt.dp)) {
        Image(bitmap = imageBitmap, contentDescription = null)
    }
}