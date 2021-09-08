package com.example.flashcard.database

import android.app.Application
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.flashcard.Action
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
): ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val question: MutableState<String> = mutableStateOf("")
    val reponse: MutableState<String> = mutableStateOf("")


    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val  _searchCards = MutableStateFlow<RequestState<List<Card>>>(RequestState.Idle)
    val searchCards: StateFlow<RequestState<List<Card>>> = _searchCards

    private val  _allCards = MutableStateFlow<RequestState<List<Card>>>(RequestState.Idle)
    val allCards: StateFlow<RequestState<List<Card>>> = _allCards

    fun readAll(){
        _allCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.readAll.collect { _allCards.value = RequestState.Success(it) }
            }
        }catch (e: Exception){
            _allCards.value = RequestState.Error(e)
        }
    }

    fun searchDatabase(searchQuery: String){
        _searchCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchCards->
                        _searchCards.value = RequestState.Success(searchCards)
                    }
            }
        }catch (e: Exception){
        _searchCards.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }


    private val _selectedCard: MutableStateFlow<Card?> = MutableStateFlow(null)
    val selectedCard: StateFlow<Card?> = _selectedCard
    fun getSelected(cardId: Int){
        viewModelScope.launch{
            repository.getSelected(cardId = cardId).collect {
                card-> _selectedCard.value = card            }
        }
    }

    private fun insert(){
        viewModelScope.launch {
            val card = Card(
                question = question.value,
                reponse = reponse.value
            )
            repository.insert(card = card)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateCard(){
        viewModelScope.launch(Dispatchers.IO){
            val card  =Card(
                cardId = id.value,
                question = question.value,
                reponse = reponse.value
            )
            repository.update(card = card)
        }
    }

    private fun deleteCard(){
        viewModelScope.launch(Dispatchers.IO){
            val card  =Card(
                cardId = id.value,
                question = question.value,
                reponse = reponse.value
            )
            repository.deleteCard(card = card)
        }
    }

    fun handleDatabaseAction(action: Action){
        when(action){
            Action.ADD->{
                insert()
            }
            Action.UPDATE->{
                updateCard()
            }
            Action.DELETE->{
                deleteCard()
            }
            Action.UNDO->{
                insert()
            }
            else->{

            }
        }
        this.action.value = Action.NO_ACTION
    }
    fun update(selectedCard: Card?){
        if (selectedCard != null){
            id.value = selectedCard.cardId
            question.value = selectedCard.question
            reponse.value = selectedCard.reponse
        }else{
            id.value = 0
            reponse.value = ""
            question.value = ""
        }
    }
    fun validateFields(): Boolean{
        return question.value.isNotEmpty() && reponse.value.isNotEmpty()
    }


}