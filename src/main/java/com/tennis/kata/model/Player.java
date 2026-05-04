package com.tennis.kata.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Player {

    private final String name;
    private Score score = Score.LOVE;

    public void incrementScore() {
        this.score = score.next();
    }
}
