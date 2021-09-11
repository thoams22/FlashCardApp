package com.example.flashcard.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: Card)

    @Update
    suspend fun update(card: Card)

    @Query("SELECT * from Card")
    fun readAllCard(): Flow<List<Card>>

    @Delete
    suspend fun deleteCard(card: Card)

    @Transaction
    @Query("SELECT * from Card where folderName = :folderName AND (question LIKE :searchQuery OR reponse LIKE :searchQuery)")
    fun searchDatabaseCard(searchQuery: String, folderName: String): Flow<List<Card>>

    @Query("SELECT * from Card where cardId = :cardId ")
    fun getSelectedCard(cardId: Int): Flow<Card>

    @Query("SELECT * from Folder")
    fun readAllFolder(): Flow<List<Folder>>

    @Query("SELECT * from Folder where folderName LIKE :searchQuery")
    fun searchDatabaseFolder(searchQuery: String): Flow<List<Folder>>

    @Transaction
    @Query("SELECT * from Folder where folderName = :folderName")
    fun getFolderWithCards(folderName: String): Flow<List<FolderWithCards>>

    @Query("SELECT * from Folder where folderName = :folderName ")
    fun getSelectedFolder(folderName: String?): Flow<Folder>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFolder(folder: Folder)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)
}