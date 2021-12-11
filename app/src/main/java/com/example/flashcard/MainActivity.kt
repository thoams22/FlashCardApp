package com.example.flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashcard.database.CardViewModel
import com.example.flashcard.navigation.SetupNavigation
import com.example.flashcard.ui.theme.FlashCardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        setContent {
            FlashCardTheme(darkTheme = true) {
                navController = rememberNavController()
                SetupNavigation(navController = navController,cardviewModel = cardViewModel)
            }
        }
    }
}
