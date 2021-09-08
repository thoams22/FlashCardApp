package com.example.flashcard

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    UNDO,
    NO_ACTION
}

fun String?.toAction(): Action{
    return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        this == "NO_ACTION" -> {
            Action.NO_ACTION
        }
        else -> {
            Action.NO_ACTION
        }
    }
}