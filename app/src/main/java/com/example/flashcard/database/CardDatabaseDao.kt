package com.example.flashcard.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: Card)

    @Update
    suspend fun update(card: Card)

    @Query("SELECT * from card_table")
    fun readAll(): Flow<List<Card>>

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * from card_table where question LIKE :searchQuery OR reponse LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Card>>

    @Query("SELECT * from card_table where cardId = :cardId ")
    fun getSelected(cardId: Int): Flow<Card>

}