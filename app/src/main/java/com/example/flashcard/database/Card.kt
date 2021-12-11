package com.example.flashcard.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Card (
    @PrimaryKey(autoGenerate = true)
    val cardId: Int=0,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name="reponse")
    val reponse: String,
    val folderId: Int,
    val questionRelativePosition: String,
    val reponseRelativePosition: String
)