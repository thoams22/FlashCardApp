package com.example.flashcard.database

import androidx.room.Embedded
import androidx.room.Relation


data class FolderWithCards(
    @Embedded val folder: Folder,
    @Relation(
        parentColumn = "folderId",
        entityColumn = "folderId"
    )
    val cards: List<Card>
)
