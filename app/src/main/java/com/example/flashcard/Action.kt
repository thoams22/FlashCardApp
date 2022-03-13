package com.example.flashcard

enum class Action {
    ADD_CARD,
    UPDATE_CARD,
    DELETE_CARD,
    UNDO_CARD,
    NO_ACTION,
    ADD_FOLDER,
    UPDATE_FOLDER,
    DELETE_FOLDER
}

fun String?.toAction(): Action{
    return when {
        this == "ADD_CARD" -> {
            Action.ADD_CARD
        }
        this == "UPDATE_CARD" -> {
            Action.UPDATE_CARD
        }
        this == "DELETE_CARD" -> {
            Action.DELETE_CARD
        }
        this == "UNDO_CARD" -> {
            Action.UNDO_CARD
        }
        this == "ADD_FOLDER" -> {
            Action.ADD_FOLDER
        }
        this == "UPDATE_FOLDER" -> {
            Action.UPDATE_FOLDER
        }
        this == "DELETE_FOLDER" -> {
            Action.DELETE_FOLDER
        }
        this == "NO_ACTION" -> {
            Action.NO_ACTION
        }
        else -> {
            Action.NO_ACTION
        }
    }
}