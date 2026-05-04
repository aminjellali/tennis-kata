package com.tennis.kata;

import com.tennis.kata.engine.GameValidator;
import com.tennis.kata.engine.ScoreHandlingService;
import com.tennis.kata.engine.StandardGameValidator;
import com.tennis.kata.exception.PlayChainNotValidException;
import com.tennis.kata.model.Game;
import com.tennis.kata.model.GameState;
import com.tennis.kata.model.Player;
import com.tennis.kata.printer.ConsoleScorePrinter;
import com.tennis.kata.printer.ScorePrinter;

import java.util.List;
import java.util.Scanner;

public class TennisGame {

    private final ScorePrinter printer;

    public TennisGame(ScorePrinter printer) {
        this.printer = printer;
    }

    public void computeScore(String balls) {
        List<Character> playerSymbols = extractPlayerSymbols(balls);
        char symbolA = playerSymbols.get(0);
        char symbolB = playerSymbols.get(1);

        Player playerA = new Player(String.valueOf(symbolA));
        Player playerB = new Player(String.valueOf(symbolB));
        Game game = new Game(playerA, playerB);
        ScoreHandlingService service = ScoreHandlingService.getInstance();

        for (char ballWinner : balls.toCharArray()) {
            Player winner = ballWinner == symbolA ? playerA : playerB;
            GameState state = service.ballWonBy(game, printer, winner);
            if (state.equals(GameState.WIN)) {
                break;
            }
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
        new TennisGame(new ConsoleScorePrinter()).computeScore(ballSequence);
    }
}
