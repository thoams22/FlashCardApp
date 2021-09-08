package com.example.flashcard.database

import android.app.Application
import androidx.lifecycle.LiveData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class CardRepository @Inject constructor(private val cardDao: CardDatabaseDao){

    val readAll: Flow<List<Card>> = cardDao.readAll()

     fun getSelected(cardId: Int): Flow<Card>{
        return cardDao.getSelected(cardId)
    }

    suspend fun insert(card: Card){
        cardDao.insert(card = card)
    }

    suspend fun update(card: Card){
        cardDao.update(card = card)
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card = card)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Card>>{
        return cardDao.searchDatabase(searchQuery = searchQuery)
    }


}