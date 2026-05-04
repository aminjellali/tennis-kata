package com.tennis.kata.exception;

public class InvalidGameStateException extends RuntimeException {

    public InvalidGameStateException(String state) {
        super("Unexpected game state encountered: " + state);
    }
}
