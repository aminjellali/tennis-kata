package com.tennis.kata;

import com.tennis.kata.engine.StandardGameValidator;
import com.tennis.kata.exception.PlayChainNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TennisGameTest {

    /** Captures lines emitted by the game without touching System.out. */
    private final List<String> output = new ArrayList<>();
    private final TennisGame game = new TennisGame(new StandardGameValidator(), output::add);

    // ── Parameterized scenarios ──────────────────────────────────────────────

    static Stream<Arguments> tennisScenarios() {
        return Stream.of(
                // 1. Kata example
                Arguments.of("ABABAA",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 15 / Player B : 15
                        Player A : 30 / Player B : 15
                        Player A : 30 / Player B : 30
                        Player A : 40 / Player B : 30
                        Player A wins the game"""),

                // 2. Straight win by first player
                Arguments.of("AAAAB",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A wins the game"""),

                // 3. Straight win when second symbol appears first
                Arguments.of("BBBBA",
                        """
                        Player B : 15 / Player A : 0
                        Player B : 30 / Player A : 0
                        Player B : 40 / Player A : 0
                        Player B wins the game"""),

                // 4. Deuce → Advantage A → A wins
                Arguments.of("AAABBBAA",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A : 40 / Player B : 15
                        Player A : 40 / Player B : 30
                        Deuce
                        Advantage player A
                        Player A wins the game"""),

                // 5. Deuce → Advantage B → back to Deuce → Advantage A → A wins
                Arguments.of("AAABBBBAAA",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A : 40 / Player B : 15
                        Player A : 40 / Player B : 30
                        Deuce
                        Advantage player B
                        Deuce
                        Advantage player A
                        Player A wins the game"""),

                // 6. Deuce → Advantage B → B wins
                Arguments.of("AAABBBBB",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A : 40 / Player B : 15
                        Player A : 40 / Player B : 30
                        Deuce
                        Advantage player B
                        Player B wins the game"""),

                // 7. Game ends mid-string: extra balls are ignored
                Arguments.of("AAAABBBB",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A wins the game"""),

                // 8. Custom symbols (the kata allows any two distinct chars)
                Arguments.of("UUUIIIUI",
                        """
                        Player U : 15 / Player I : 0
                        Player U : 30 / Player I : 0
                        Player U : 40 / Player I : 0
                        Player U : 40 / Player I : 15
                        Player U : 40 / Player I : 30
                        Deuce
                        Advantage player U
                        Deuce"""),

                // 9. Many oscillations through deuce, then B wins
                Arguments.of("AAABBBABABBB",
                        """
                        Player A : 15 / Player B : 0
                        Player A : 30 / Player B : 0
                        Player A : 40 / Player B : 0
                        Player A : 40 / Player B : 15
                        Player A : 40 / Player B : 30
                        Deuce
                        Advantage player A
                        Deuce
                        Advantage player A
                        Deuce
                        Advantage player B
                        Player B wins the game""")
        );
    }

    @ParameterizedTest(name = "[{index}] input \"{0}\"")
    @MethodSource("tennisScenarios")
    void should_print_expected_output(String input, String expected) throws Exception {
        game.computeScore(input);

        assertEquals(expected.trim(), String.join("\n", output));
    }

    // ── Validation ───────────────────────────────────────────────────────────

    @Test
    void should_throw_when_sequence_has_only_one_player() {
        assertThrows(PlayChainNotValidException.class, () -> game.computeScore("AAAA"));
    }

    @Test
    void should_throw_when_sequence_too_short() {
        assertThrows(PlayChainNotValidException.class, () -> game.computeScore("AB"));
    }

    @Test
    void should_throw_when_sequence_has_more_than_two_players() {
        assertThrows(PlayChainNotValidException.class, () -> game.computeScore("ABCABC"));
    }

    @Test
    void should_throw_when_sequence_is_null_or_empty() {
        assertThrows(PlayChainNotValidException.class, () -> game.computeScore(null));
        assertThrows(PlayChainNotValidException.class, () -> game.computeScore(""));
    }
}
