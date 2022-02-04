package com.example.flashcard.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcard.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
): ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val question: MutableState<String> = mutableStateOf("")
    val reponse: MutableState<String> = mutableStateOf("")
    val folderid: MutableState<Int> = mutableStateOf(0)
    val reponseRelativePosition: MutableState<String> = mutableStateOf("")
    val questionRelativePosition: MutableState<String> = mutableStateOf("")

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")
    val preReponse: MutableState<String> = mutableStateOf(" ")
    val preReponseRelativePosition: MutableState<String> = mutableStateOf("")
    val contentState: MutableState<ContentState> = mutableStateOf(ContentState.CLOSED)
    val keyboardState: MutableState<KeyboardState> = mutableStateOf(KeyboardState.DEFAULT)
    var cardList: MutableList<Card> = mutableListOf(Card(-1, "", "", -1, "", ""))
    var revisionState: MutableState<RevisionState> = mutableStateOf(RevisionState.QUESTION)

    private val  _searchCards = MutableStateFlow<RequestState<List<Card>>>(RequestState.Idle)
    val searchCards: StateFlow<RequestState<List<Card>>> = _searchCards

    fun searchDatabaseCard(searchQuery: String){

        _searchCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.searchDatabaseCard(searchQuery = "%$searchQuery%", folderId = folderid.value)
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
        viewModelScope.launch(Dispatchers.IO) {
            val card = Card(
                question = question.value,
                reponse = reponse.value,
                folderId = folderid.value,
                reponseRelativePosition = reponseRelativePosition.value,
                questionRelativePosition = questionRelativePosition.value
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
                folderId = folderid.value,
                reponseRelativePosition = reponseRelativePosition.value,
                questionRelativePosition = questionRelativePosition.value
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
                folderId = folderid.value,
                reponseRelativePosition = reponseRelativePosition.value,
                questionRelativePosition = questionRelativePosition.value
            )
            repository.deleteCard(card = card)
        }
    }

    private fun deleteCard(card: Card){
        viewModelScope.launch(Dispatchers.IO){
            val carde  =Card(
                cardId = card.cardId,
                question = card.question,
                reponse = card.reponse,
                folderId = card.folderId,
                questionRelativePosition = questionRelativePosition.value,
                reponseRelativePosition = reponseRelativePosition.value
            )
            repository.deleteCard(card = carde)
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
            else->{
            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateSelectedCard(selectedCard: Card?, selectedFolderId: Int){
        if (selectedCard != null){
            id.value = selectedCard.cardId
            question.value = selectedCard.question
            reponse.value = selectedCard.reponse
            folderid.value = selectedCard.folderId
            reponseRelativePosition.value = selectedCard.reponseRelativePosition
            questionRelativePosition.value = selectedCard.questionRelativePosition
        }else{
            id.value = 0
            reponse.value = ""
            question.value = ""
            folderid.value = selectedFolderId
            reponseRelativePosition.value = ""
            questionRelativePosition.value = ""
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
    val ancientFolderName: MutableState<String> = mutableStateOf("")
    val folderRelativePosition: MutableState<String> = mutableStateOf("")

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
            folderid.value = selectedFolder.folderId
            folderName.value = selectedFolder.folderName
            folderRelativePosition.value = selectedFolder.folderRelativePosition
        }else{
            folderid.value = 0
            folderName.value = ""
            folderRelativePosition.value = ""
        }
        viewModelScope.launch{
            repository.getSelectedFolder(folderId = folderid.value).collect {
                    folder-> _selectedFolder.value = folder}
        }
    }

    private val _selectedFolder: MutableStateFlow<Folder?> = MutableStateFlow(null)
    var selectedFolder: StateFlow<Folder?> = _selectedFolder

    fun getSelectedFolder(folderId: Int?){
        viewModelScope.launch{
            repository.getSelectedFolder(folderId = folderId).collect {
                    folder-> _selectedFolder.value = folder}
        }
    }

    private fun insertFolder(){
        viewModelScope.launch {
            val folder = Folder(
                folderName = folderName.value,
                folderRelativePosition = folderRelativePosition.value
            )
            repository.insertFolder(folder = folder)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun deleteFolder(){
        if(getFolderWithCards.value !is RequestState.Loading && getFolderWithCards.value !is RequestState.Success){
            getFolderWithCards(folderId = folderid.value)
        }
        deleteSelectedFolder(getFolderWithCards.value)
    }

    private fun deleteSelectedFolder(folderWithcards: RequestState<List<FolderWithCards>>){
        if (folderWithcards is RequestState.Success && folderWithcards.data.isNotEmpty()){
            folderWithcards.data.first().cards.forEach{ card ->
                deleteCard(card)
            }
            viewModelScope.launch(Dispatchers.IO){
                val folder  =Folder(
                    folderId = folderid.value,
                    folderName = folderName.value,
                    folderRelativePosition = folderRelativePosition.value
                )
                repository.deleteFolder(folder = folder)
            }
            _getFolderWithCards.value = RequestState.Idle
        }
    }

    private fun updateSelectedFolder(){
        viewModelScope.launch(Dispatchers.IO){
            val folder  =Folder(
                folderId = folderid.value,
                folderName = folderName.value,
                folderRelativePosition = folderRelativePosition.value
            )
            repository.updateFolder(folder = folder)
        }
    }

    private val  _getFolderWithCards = MutableStateFlow<RequestState<List<FolderWithCards>>>(RequestState.Idle)
    var getFolderWithCards: StateFlow<RequestState<List<FolderWithCards>>> = _getFolderWithCards

    fun getFolderWithCards(folderId: Int){
        _getFolderWithCards.value = RequestState.Loading
        try {
            viewModelScope.launch{
                repository.getFolderWithCards(folderId = folderId).collect { _getFolderWithCards.value = RequestState.Success(it) }
            }
        }catch (e: Exception){
            _getFolderWithCards.value = RequestState.Error(e)
        }
    }
}