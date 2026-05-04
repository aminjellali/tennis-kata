package com.tennis.kata.engine;

import com.tennis.kata.exception.PlayChainNotValidException;

import java.util.HashSet;
import java.util.Set;

public class StandardGameValidator implements GameValidator {

    private static final int PLAYERS_NUMBER = 2;
    private static final int MIN_BALLS = 4;

    @Override
    public void validate(String ballSequence) throws PlayChainNotValidException {
        if (ballSequence == null || ballSequence.isBlank()) {
            throw new PlayChainNotValidException("Ball sequence cannot be empty.");
        }

        Set<Character> uniqueChars = new HashSet<>();
        for (char c : ballSequence.toCharArray()) {
            uniqueChars.add(c);
        }

        if (uniqueChars.size() != PLAYERS_NUMBER) {
            throw new PlayChainNotValidException(
                    "Ball sequence must represent exactly two players");
        }

        if (ballSequence.length() < MIN_BALLS) {
            throw new PlayChainNotValidException(
                    "Ball sequence is too short — a game requires at least " + MIN_BALLS + " balls.");
        }
    }
}
