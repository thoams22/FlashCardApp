package com.example.flashcard.database

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.flashcard.Action
import com.example.flashcard.RequestState
import com.example.flashcard.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

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

    fun searchDatabaseCard(searchQuery: String){

        _searchCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.searchDatabaseCard(searchQuery = "%$searchQuery%", folderName = folderName.value)
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

    fun getSelectedCard(cardId: Int){
        viewModelScope.launch{
            repository.getSelectedCard(cardId = cardId).collect {
                card-> _selectedCard.value = card            }
        }
    }

    private fun insertCard(){
        viewModelScope.launch {
            val card = Card(
                question = question.value,
                reponse = reponse.value,
                folderName = folderName.value
            )
            repository.insertCard(card = card)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateSelectedCard(){
        viewModelScope.launch(Dispatchers.IO){
            val card  =Card(
                cardId = id.value,
                question = question.value,
                reponse = reponse.value ,
                folderName = folderName.value
            )
            repository.updateCard(card = card)
        }
    }

    private fun deleteCard(){
        viewModelScope.launch(Dispatchers.IO){
            val card  =Card(
                cardId = id.value,
                question = question.value,
                reponse = reponse.value,
                folderName = folderName.value
            )
            repository.deleteCard(card = card)
        }
    }

    fun handleDatabaseAction(action: Action){
        when(action){
            Action.ADD_CARD->{
                insertCard()
            }
            Action.UPDATE_CARD->{
                updateSelectedCard()
            }
            Action.DELETE_CARD->{
                deleteCard()
            }
            Action.UNDO_CARD->{
                insertCard()
            }
            Action.ADD_FOLDER->{
                insertFolder()
            }
            Action.UPDATE_FOLDER->{
                updateSelectedFolder()
            }
            Action.DELETE_FOLDER->{
                deleteFolder()
            }
            Action.UNDO_FOLDER->{
                insertFolder()
            }
            else->{
            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateSelectedCard(selectedCard: Card?, selectedFolder: String){
        if (selectedCard != null){
            id.value = selectedCard.cardId
            question.value = selectedCard.question
            reponse.value = selectedCard.reponse
            folderName.value = selectedFolder
        }else{
            id.value = 0
            reponse.value = ""
            question.value = ""
            folderName.value = selectedFolder
        }
    }
    fun validateFields(): Boolean{
        return question.value.isNotEmpty() && reponse.value.isNotEmpty()
    }

    fun validateFolderFields(): Boolean{
        return folderName.value.isNotEmpty()
    }

    private val  _allFolders = MutableStateFlow<RequestState<List<Folder>>>(RequestState.Idle)
    val allFolders: StateFlow<RequestState<List<Folder>>> = _allFolders

    fun readAllFolder(){
        _allFolders.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.readAllFolder.collect { _allFolders.value = RequestState.Success(it) }
            }
        }catch (e: Exception){
            _allFolders.value = RequestState.Error(e)
        }
    }

    private val  _searchFolders = MutableStateFlow<RequestState<List<Folder>>>(RequestState.Idle)
    val searchFolders: StateFlow<RequestState<List<Folder>>> = _searchFolders

    val folderName: MutableState<String> = mutableStateOf("")

    fun searchDatabaseFolder(searchQuery: String){
        _searchFolders.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.searchDatabaseFolder(searchQuery = "%$searchQuery%")
                    .collect { searchFolders->
                        _searchFolders.value = RequestState.Success(searchFolders)
                    }
            }
        }catch (e: Exception){
            _searchFolders.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun updateSelectedFolder(selectedFolder: Folder?){

        if (selectedFolder != null){
            folderName.value = selectedFolder.folderName
        }else{
            folderName.value = ""
        }
        viewModelScope.launch{
            repository.getSelectedFolder(folderName = folderName.value).collect {
                    folder-> _selectedFolder.value = folder}
        }
    }

    private val _selectedFolder: MutableStateFlow<Folder?> = MutableStateFlow(null)
    val selectedFolder: StateFlow<Folder?> = _selectedFolder


    fun getSelectedFolder(folderName: String?){
        viewModelScope.launch{
            repository.getSelectedFolder(folderName = folderName).collect {
                    folder-> _selectedFolder.value = folder}
        }
    }

    private fun insertFolder(){
        viewModelScope.launch {
            val folder = Folder(
                folderName = folderName.value
            )
            repository.insertFolder(folder = folder)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun deleteFolder(){
        viewModelScope.launch(Dispatchers.IO){
            val folder  =Folder(
                folderName = folderName.value
            )
            repository.deleteFolder(folder = folder)
        }
    }

    private fun updateSelectedFolder(){
        viewModelScope.launch(Dispatchers.IO){
            val folder  =Folder(
                folderName = folderName.value
            )
            repository.updateFolder(folder = folder)
        }
    }

    private val  _getFolderWithCards = MutableStateFlow<RequestState<List<FolderWithCards>>>(RequestState.Idle)
    val getFolderWithCards: StateFlow<RequestState<List<FolderWithCards>>> = _getFolderWithCards

    fun getFolderWithCards(folderName: String){
        _getFolderWithCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.getFolderWithCards(folderName = folderName).collect { _getFolderWithCards.value = RequestState.Success(it) }
            }
        }catch (e: Exception){
            _getFolderWithCards.value = RequestState.Error(e)
        }
    }

}