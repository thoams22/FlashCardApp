package com.example.flashcard.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "card_table")
data class Card (
    @PrimaryKey(autoGenerate = true)
    var cardId: Int=0,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name="reponse")
    val reponse: String
)