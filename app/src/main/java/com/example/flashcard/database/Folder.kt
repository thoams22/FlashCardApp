package com.example.flashcard.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "folderId")
    val folderId: Int = 0,
    val folderName: String,
    val folderRelativePosition: String
)
