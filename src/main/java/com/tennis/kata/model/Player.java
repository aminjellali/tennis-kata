package com.tennis.kata.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class Player {

    private final Character name;
    private Score score = Score.LOVE;

    /** Advances this player's score by one step in the natural progression. */
    public void incrementScore() {
        this.score = score.next();
    }

    /** Sets this player's score to {@link Score#WIN}. 
     * will be used mainly to skip advantage when player wins
    */
    public void win() {
        this.score = Score.WIN;
    }

    /**
     * Drops the player from ADVANTAGE back to FORTY (used when the opponent
     * scores during an advantage state).
     *
     * @throws IllegalStateException if the player is not currently at ADVANTAGE
     */
    public void loseAdvantage() {
        if (this.score != Score.ADVANTAGE) {
            throw new IllegalStateException(
                    "Cannot lose advantage: player '" + name +
                            "' is not at ADVANTAGE (current score: " + score + ")");
        }
        this.score = Score.FORTY;
    }

    public boolean hasWon() {
        return score == Score.WIN;
    }

    public boolean hasAdvantage() {
        return score == Score.ADVANTAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
