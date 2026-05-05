package com.tennis.kata.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void new_player_starts_at_love() {
        Player p = new Player('A');
        assertEquals(Score.LOVE, p.getScore());
    }

    @Test
    void incrementScore_advances_progression() {
        Player p = new Player('A');
        p.incrementScore();
        assertEquals(Score.FIFTEEN, p.getScore());
        p.incrementScore();
        assertEquals(Score.THIRTY, p.getScore());
        p.incrementScore();
        assertEquals(Score.FORTY, p.getScore());
        p.incrementScore();
        assertEquals(Score.ADVANTAGE, p.getScore());
    }

    @Test
    void win_sets_score_to_win() {
        Player p = new Player('A');
        p.win();
        assertEquals(Score.WIN, p.getScore());
        assertTrue(p.hasWon());
    }

    @Test
    void hasAdvantage_true_only_at_advantage() {
        Player p = new Player('A');
        assertFalse(p.hasAdvantage());

        // advance to ADVANTAGE: LOVE → 15 → 30 → 40 → ADV (4 increments)
        for (int i = 0; i < 4; i++) p.incrementScore();
        assertTrue(p.hasAdvantage());
    }

    @Test
    void loseAdvantage_drops_back_to_forty() {
        Player p = new Player('A');
        for (int i = 0; i < 4; i++) p.incrementScore();
        assertEquals(Score.ADVANTAGE, p.getScore());

        p.loseAdvantage();
        assertEquals(Score.FORTY, p.getScore());
    }

    @Test
    void loseAdvantage_throws_when_not_at_advantage() {
        Player p = new Player('A'); // at LOVE
        IllegalStateException ex = assertThrows(IllegalStateException.class, p::loseAdvantage);
        assertTrue(ex.getMessage().contains("ADVANTAGE"));
    }

    @Test
    void equals_and_hashCode_use_name() {
        Player a1 = new Player('A');
        Player a2 = new Player('A');
        Player b = new Player('B');

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, b);
    }

    @Test
    void equals_handles_null_and_other_types() {
        Player a = new Player('A');
        assertNotEquals(a, null);
        assertNotEquals(a, "A");
        assertEquals(a, a);
    }
}
