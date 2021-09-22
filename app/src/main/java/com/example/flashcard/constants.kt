package com.example.flashcard

object constants {
    const val LIST_SCREEN = "list/{folderName}/{action}"
    const val CARD_SCREEN = "task/{cardId}"
    const val FOLDER_LIST_SCREEN = "folderList/{action}"
    const val FOLDER_SCREEN = "folder/{folderName}"
    const val LEARNING_SCREEN = "learning/{folderId}"

    const val LIST_ARGUMENT_KEY = "folderName"
    const val LIST_ACTION_KEY = "action"
    const val CARD_ARGUMENT_KEY = "cardId"
    const val FOLDER_LIST_ARGUMENT_KEY = "action"
    const val FOLDER_ARGUMENT_KEY = "folderName"
    const val LEARNING_ARGUMENT_KEY = "folderId"
}