package com.tennis.kata.engine;

import com.tennis.kata.exception.PlayChainNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StandardGameValidatorTest {

    private StandardGameValidator validator;

    @BeforeEach
    void setUp() {
        validator = new StandardGameValidator();
    }

    // ── Error cases ───────────────────────────────────────────────────────────

    @ParameterizedTest(name = "blank or empty input: \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void throws_whenInputIsBlankOrEmpty(String input) {
        PlayChainNotValidException ex = assertThrows(
                PlayChainNotValidException.class, () -> validator.validate(input));

        assertTrue(ex.getMessage().contains("empty"));
    }

    @ParameterizedTest(name = "only one unique character: \"{0}\"")
    @ValueSource(strings = {"AAAA", "BBBB", "XXXX", "1111"})
    void throws_whenSequenceHasOnlyOneUniqueCharacter(String input) {
        PlayChainNotValidException ex = assertThrows(
                PlayChainNotValidException.class, () -> validator.validate(input));

        assertTrue(ex.getMessage().contains("two players"));
    }

    @ParameterizedTest(name = "more than two unique characters: \"{0}\"")
    @ValueSource(strings = {"ABCABC", "AABBCC", "ABCDE", "ABZ"})
    void throws_whenSequenceHasMoreThanTwoUniqueCharacters(String input) {
        PlayChainNotValidException ex = assertThrows(
                PlayChainNotValidException.class, () -> validator.validate(input));

        assertTrue(ex.getMessage().contains("two players"));
    }

    @ParameterizedTest(name = "sequence too short: \"{0}\"")
    @ValueSource(strings = {"AB", "ABA", "BAB"})
    void throws_whenSequenceIsTooShort(String input) {
        PlayChainNotValidException ex = assertThrows(
                PlayChainNotValidException.class, () -> validator.validate(input));

        assertTrue(ex.getMessage().contains("too short"));
    }

    // ── Happy path ────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "valid sequence: \"{0}\"")
    @ValueSource(strings = {"ABAB", "ABABAA", "UUUIIII", "BBBBAAA", "HHHHHJJJJJ"})
    void doesNotThrow_whenSequenceIsValid(String input) {
        assertDoesNotThrow(() -> validator.validate(input));
    }
}
