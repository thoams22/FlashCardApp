package com.example.flashcard.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Card::class, Folder::class],
    version = 1,
    exportSchema = false
)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDatabaseDao(): CardDatabaseDao

}