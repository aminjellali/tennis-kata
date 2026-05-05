package com.tennis.kata.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreTest {

    @ParameterizedTest(name = "{0}.next() == {1}")
    @CsvSource({
            "LOVE,      FIFTEEN",
            "FIFTEEN,   THIRTY",
            "THIRTY,    FORTY",
            "FORTY,     ADVANTAGE",
            "ADVANTAGE, WIN"
    })
    void next_advances_to_expected_score(Score current, Score expected) {
        assertEquals(expected, current.next());
    }

    @Test
    void win_cannot_advance() {
        assertThrows(IllegalStateException.class, Score.WIN::next);
    }

    @ParameterizedTest(name = "{0} -> \"{1}\"")
    @CsvSource({
            "LOVE,      0",
            "FIFTEEN,   15",
            "THIRTY,    30",
            "FORTY,     40",
            "ADVANTAGE, 50",
            "WIN,       100"
    })
    void toString_returns_numeric_value(Score score, String expected) {
        assertEquals(expected, score.toString());
    }
}
