package com.example.flashcard.database

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class CardRepository @Inject constructor(private val cardDao: CardDatabaseDao){

    val readAllCard: Flow<List<Card>> = cardDao.readAllCard()

     fun getSelectedCard(cardId: Int): Flow<Card>{
        return cardDao.getSelectedCard(cardId)
    }

    suspend fun insertCard(card: Card){
        cardDao.insertCard(card = card)
    }

    suspend fun updateCard(card: Card){
        cardDao.update(card = card)
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card = card)
    }

    val readAllFolder: Flow<List<Folder>> = cardDao.readAllFolder()

    fun searchDatabaseCard(searchQuery: String, folderId: Int): Flow<List<Card>>{
        return cardDao.searchDatabaseCard(searchQuery = searchQuery, folderId = folderId)
    }


    fun searchDatabaseFolder(searchQuery: String): Flow<List<Folder>>{
        return cardDao.searchDatabaseFolder(searchQuery = searchQuery)
    }

    fun getSelectedFolder(folderId: Int?): Flow<Folder>{
        return cardDao.getSelectedFolder(folderId)
    }

    suspend fun insertFolder(folder: Folder){
        cardDao.insertFolder(folder = folder)
    }

    suspend fun deleteFolder(folder: Folder){
        cardDao.deleteFolder(folder = folder)
    }

    suspend fun updateFolder(folder: Folder){
        cardDao.updateFolder(folder = folder)
    }

    fun getFolderWithCards(folderId: Int): Flow<List<FolderWithCards>>{
        return cardDao.getFolderWithCards(folderId)}

}