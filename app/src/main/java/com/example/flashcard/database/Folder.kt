package com.example.flashcard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(
    @PrimaryKey(autoGenerate = false)
    val folderName: String
)
