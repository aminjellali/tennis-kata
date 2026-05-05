package com.tennis.kata;

import com.tennis.kata.engine.GameValidator;
import com.tennis.kata.engine.StandardGameValidator;
import com.tennis.kata.exception.GameAlreadyFinishedException;
import com.tennis.kata.exception.PlayChainNotValidException;
import com.tennis.kata.model.Game;
import com.tennis.kata.model.Player;
import com.tennis.kata.model.Score;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class TennisGame {

    private final GameValidator validator;
    private final Consumer<String> output;

    public TennisGame() {
        this(new StandardGameValidator(), System.out::println);
    }

    public TennisGame(GameValidator validator, Consumer<String> output) {
        this.validator = validator;
        this.output = output;
    }

    /**
     * Runs the game from a sequence of ball-winner characters.
     * Each character must be one of exactly two distinct symbols.
     * Output (one line per ball, plus the final winner) is sent to the
     * configured sink.
     *
     * @throws PlayChainNotValidException if the sequence is invalid
     */
    public void computeScore(String gameSequence) throws PlayChainNotValidException {
        validator.validate(gameSequence);

        List<Character> symbols = extractPlayerSymbols(gameSequence);
        Player firstPlayer = new Player(symbols.get(0));
        Player secondPlayer = new Player(symbols.get(1));
        Game game = new Game(firstPlayer, secondPlayer);

        for (char ball : gameSequence.toCharArray()) {
            if (game.isWon()) {
                break;
            }
            playBall(game, ball, firstPlayer, secondPlayer);
            output.accept(game.renderScore());
        }
    }

    private void playBall(Game game, char ball, Player first, Player second) {
        if (game.isWon()) {
            throw new GameAlreadyFinishedException();
        }

        Player winner = (ball == first.getName()) ? first : second;
        Player loser = (winner == first) ? second : first;

        Optional<Player> advantage = game.playerWithAdvantage();

        if (advantage.isEmpty()) {
            // No advantage in play: either deuce-bound or a regular point.
            // Winning a point at FORTY (when not deuce) means winning the game outright.
            if (winner.getScore() == Score.FORTY && !game.isDeuce()) {
                winner.win();
            } else {
                winner.incrementScore();
            }
        } else if (advantage.get().equals(winner)) {
            // Player with advantage scores again → wins the game.
            winner.win();
        } else {
            // Opponent of advantage-holder scores → back to deuce.
            loser.loseAdvantage();
        }
    }

    private List<Character> extractPlayerSymbols(String balls) {
        return balls.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .toList();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameValidator validator = new StandardGameValidator();

        System.out.println("==========================================");
        System.out.println("         Welcome to Wimbledon!           ");
        System.out.println("==========================================");
        System.out.println("Enter a sequence of characters representing each ball won.");
        System.out.println("The sequence must contain exactly two distinct characters.");
        System.out.println("Example: ABABAA  or  UUUIII\n");

        String ballSequence = "";
        boolean valid = false;

        while (!valid) {
            System.out.print("Please enter the play chain: ");
            ballSequence = scanner.nextLine().trim();
            try {
                validator.validate(ballSequence);
                valid = true;
            } catch (PlayChainNotValidException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Please try again.\n");
            }
        }

        System.out.println();
        try {
            new TennisGame().computeScore(ballSequence);
        } catch (PlayChainNotValidException e) {
            // Should never happen — validation already passed above.
            System.out.println("Unexpected validation error: " + e.getMessage());
        }finally{
            scanner.close();
        }
    }
}
