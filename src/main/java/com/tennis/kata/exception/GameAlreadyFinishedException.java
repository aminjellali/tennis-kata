package com.tennis.kata.exception;

public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException() {
        super("Cannot play a ball: the game is already finished.");
    }
}
