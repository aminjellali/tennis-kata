package com.tennis.kata.model;

import com.tennis.kata.exception.InvalidGameStateException;

import java.text.MessageFormat;
import java.util.Optional;

public record Game(Player playerA, Player playerB) {

    public boolean isDeuce() {
        return playerA.getScore() == Score.FORTY
                && playerB.getScore() == Score.FORTY;
    }

    public boolean inProgress() {
        return !isWon(); 
    }

    public boolean isWon() {
        return playerA.hasWon() || playerB.hasWon();
    }

    public Optional<Player> playerWithAdvantage() {
        boolean playerAHasAdvanatge = playerA.hasAdvantage();
        boolean playerBHasAdvanatge = playerB.hasAdvantage();
        if (playerAHasAdvanatge && playerBHasAdvanatge) {
            throw new InvalidGameStateException(
                    "Both players cannot have advantage at the same time.");
        }
        if (playerAHasAdvanatge) return Optional.of(playerA);
        if (playerBHasAdvanatge) return Optional.of(playerB);
        return Optional.empty();
    }

    /**
     * Returns the human-readable score line for the current state.
     * Pure function — no I/O. The caller decides where to send the output.
     */
    public String renderScore() {
        if (isWon()) {
            Player winner = playerA.hasWon() ? playerA : playerB;
            return MessageFormat.format("Player {0} wins the game", winner.getName());
        }

        Optional<Player> advantage = playerWithAdvantage();
        if (advantage.isPresent()) {
            return MessageFormat.format("Advantage player {0}", advantage.get().getName());
        }

        if (isDeuce()) {
            return "Deuce";
        }

        return MessageFormat.format("Player {0} : {1} / Player {2} : {3}",
                playerA.getName(), playerA.getScore(),
                playerB.getName(), playerB.getScore());
    }
}
