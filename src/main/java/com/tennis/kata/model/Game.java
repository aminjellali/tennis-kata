package com.tennis.kata.model;

import lombok.Getter;

@Getter
public class Game {

    private final Player playerA;
    private final Player playerB;
    
    public Game(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

}
