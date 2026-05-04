package com.tennis.kata.printer;

import com.tennis.kata.model.Player;

public class ConsoleScorePrinter implements ScorePrinter {

    @Override
    public void printScore(Player playerA, Player playerB) {
        System.out.printf("Player %s : %d / Player %s : %d%n",
                playerA.getName(), playerA.getScore().getValue(),
                playerB.getName(), playerB.getScore().getValue());
    }

    @Override
    public void printDeuce() {
        System.out.println("Deuce");
    }

    @Override
    public void printAdvantage(Player player) {
        System.out.printf("Advantage Player %s%n", player.getName());
    }

    @Override
    public void printWinner(Player player) {
        System.out.printf("Player %s wins the game%n", player.getName());
    }
}
