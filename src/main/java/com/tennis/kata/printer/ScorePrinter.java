package com.tennis.kata.printer;

import com.tennis.kata.model.Player;

public interface ScorePrinter {

    void printScore(Player playerA, Player playerB);

    void printDeuce();

    void printAdvantage(Player player);

    void printWinner(Player player);
}
