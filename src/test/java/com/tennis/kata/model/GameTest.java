package com.tennis.kata.model;

import com.tennis.kata.exception.InvalidGameStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Player at(char name, Score score) {
        Player p = new Player(name);
        // Step through to the desired score. Sufficient for tests since
        // increments respect the natural progression.
        while (p.getScore() != score) {
            if (p.getScore() == Score.WIN) {
                throw new IllegalArgumentException("Cannot go past WIN in helper.");
            }
            p.incrementScore();
        }
        return p;
    }

    @Test
    void renderScore_initial_state() {
        Game g = new Game(new Player('A'), new Player('B'));
        assertEquals("Player A : 0 / Player B : 0", g.renderScore());
    }

    @Test
    void renderScore_progress() {
        Game g = new Game(at('A', Score.THIRTY), at('B', Score.FIFTEEN));
        assertEquals("Player A : 30 / Player B : 15", g.renderScore());
    }

    @Test
    void renderScore_deuce() {
        Game g = new Game(at('A', Score.FORTY), at('B', Score.FORTY));
        assertTrue(g.isDeuce());
        assertEquals("Deuce", g.renderScore());
    }

    @Test
    void renderScore_advantage_a() {
        Game g = new Game(at('A', Score.ADVANTAGE), at('B', Score.FORTY));
        assertEquals("Advantage player A", g.renderScore());
    }

    @Test
    void renderScore_advantage_b() {
        Game g = new Game(at('A', Score.FORTY), at('B', Score.ADVANTAGE));
        assertEquals("Advantage player B", g.renderScore());
    }

    @Test
    void renderScore_winner_a() {
        Player a = new Player('A');
        a.win();
        Game g = new Game(a, new Player('B'));
        assertTrue(g.isWon());
        assertFalse(g.inProgress());
        assertEquals("Player A wins the game", g.renderScore());
    }

    @Test
    void renderScore_winner_b() {
        Player b = new Player('B');
        b.win();
        Game g = new Game(new Player('A'), b);
        assertEquals("Player B wins the game", g.renderScore());
    }

    @Test
    void playerWithAdvantage_returns_empty_when_neither() {
        Game g = new Game(new Player('A'), new Player('B'));
        assertTrue(g.playerWithAdvantage().isEmpty());
    }

    @Test
    void playerWithAdvantage_returns_a() {
        Game g = new Game(at('A', Score.ADVANTAGE), at('B', Score.FORTY));
        assertEquals('A', g.playerWithAdvantage().orElseThrow().getName());
    }

    @Test
    void playerWithAdvantage_returns_b() {
        Game g = new Game(at('A', Score.FORTY), at('B', Score.ADVANTAGE));
        assertEquals('B', g.playerWithAdvantage().orElseThrow().getName());
    }

    @Test
    void playerWithAdvantage_throws_when_both_at_advantage() {
        Game g = new Game(at('A', Score.ADVANTAGE), at('B', Score.ADVANTAGE));
        assertThrows(InvalidGameStateException.class, g::playerWithAdvantage);
    }
}
