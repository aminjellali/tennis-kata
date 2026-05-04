package com.tennis.kata.engine;

import com.tennis.kata.exception.PlayChainNotValidException;

public interface GameValidator {

    void validate(String ballSequence) throws PlayChainNotValidException;
}
