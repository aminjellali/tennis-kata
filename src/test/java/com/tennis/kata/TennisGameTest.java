package com.tennis.kata;

import com.tennis.kata.model.Player;
import com.tennis.kata.printer.ScorePrinter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TennisGameTest {

    // ── Happy path ────────────────────────────────────────────────────────────

    @Test
    void playerA_wins_straight_ABABAA() {
        assertEquals(List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Player A wins the game"
        ), play("ABABAA"));
    }

    @Test
    void playerB_wins_ABBBB() {
        assertEquals(List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 15 / Player B : 30",
                "Player A : 15 / Player B : 40",
                "Player B wins the game"
        ), play("ABBBB"));
    }

    @Test
    void deuce_is_reached_AABBABAA() {
        assertEquals(List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 30 / Player B : 0",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Deuce"
        ), play("AAABBB"));
    }

    @Test
    void playerA_wins_from_advantage_AAABBBAA() {
        assertEquals(List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 30 / Player B : 0",
                "Player A : 40 / Player B : 0",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage Player A",
                "Player A wins the game"
        ), play("AAABBBAA"));
    }

    @Test
    void playerB_wins_from_advantage_AAABBBBB() {
        assertEquals(List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 30 / Player B : 0",
                "Player A : 40 / Player B : 0",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage Player B",
                "Player B wins the game"
        ), play("AAABBBBB"));
    }

    // ── Edge cases ────────────────────────────────────────────────────────────

    @Test
    void back_to_deuce_after_advantage_AAABBBAB() {
        List<String> output = play("AAABBBAB");

        assertEquals("Advantage Player A", output.get(output.size() - 2));
        assertEquals("Deuce",              output.get(output.size() - 1));
    }

    @Test
    void multiple_deuce_cycles_playerA_wins_AAABBBABABAA() {
        List<String> output = play("AAABBBABABAA");

        long deuceCount     = output.stream().filter("Deuce"::equals).count();
        long advantageCount = output.stream().filter(l -> l.startsWith("Advantage")).count();

        assertEquals(3, deuceCount);
        assertEquals(3, advantageCount);
        assertEquals("Player A wins the game", output.get(output.size() - 1));
    }

    @Test
    void extra_balls_after_game_over_are_ignored_ABABAAXX() {
        assertEquals(play("ABABAA"), play("ABABAABBBB"));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private List<String> play(String sequence) {
        List<String> output = new ArrayList<>();

        ScorePrinter spy = new ScorePrinter() {
            @Override public void printScore(Player a, Player b) {
                output.add(String.format("Player %s : %d / Player %s : %d",
                        a.getName(), a.getScore().getValue(),
                        b.getName(), b.getScore().getValue()));
            }
            @Override public void printDeuce()             { output.add("Deuce"); }
            @Override public void printAdvantage(Player p) { output.add("Advantage Player " + p.getName()); }
            @Override public void printWinner(Player p)    { output.add("Player " + p.getName() + " wins the game"); }
        };

        new TennisGame(spy).computeScore(sequence);
        return output;
    }
}
