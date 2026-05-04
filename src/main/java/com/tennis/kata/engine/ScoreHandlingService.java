package com.tennis.kata.engine;

import com.tennis.kata.model.Game;
import com.tennis.kata.model.GameState;
import com.tennis.kata.model.Player;
import com.tennis.kata.model.Score;
import com.tennis.kata.printer.ScorePrinter;

public class ScoreHandlingService {

    private static final ScoreHandlingService INSTANCE = new ScoreHandlingService();

    private ScoreHandlingService() {}

    public static ScoreHandlingService getInstance() {
        return INSTANCE;
    }

 public GameState ballWonBy(Game game, ScorePrinter printer, Player winner) {
    System.out.println(winner.getScore() + " " +opponent(game, winner).getScore());
       if (winner.getScore() == Score.ADVANTAGE) {
           printer.printWinner(winner);
           return GameState.WIN;
       }

       incrementBallWinnerScore(game, winner);
       GameState newState = resolveGameState(game);
       printResult(game, printer, winner, newState);
       return newState;
   }
   private void incrementBallWinnerScore(Game game, Player winner) {
       Player opponent = opponent(game, winner);
       Score winnerScore  = winner.getScore();
       Score opponentScore = opponent.getScore();
       if (opponentScore == Score.ADVANTAGE) {
           opponent.setScore(Score.FORTY);
       } else if (winnerScore == Score.FORTY && opponentScore == Score.FORTY) {
           winner.setScore(Score.ADVANTAGE);
       } else if (winnerScore != Score.FORTY) {
           winner.setScore(winnerScore.next());
       }
   }
 private GameState resolveGameState(Game game) {
       Score scoreA = game.getPlayerA().getScore();
       Score scoreB = game.getPlayerB().getScore();
    if (scoreA == Score.ADVANTAGE) return GameState.ADVANTAGE_A;
       if (scoreB == Score.ADVANTAGE) return GameState.ADVANTAGE_B;
       if (scoreA == Score.FORTY && scoreB == Score.FORTY) return GameState.DEUCE;
       if (scoreA == Score.FORTY || scoreB == Score.FORTY) return GameState.WIN;
       return GameState.NORMAL;
   }

   private void printResult(Game game, ScorePrinter printer, Player winner, GameState state) {
       switch (state) {
           case NORMAL      -> printer.printScore(game.getPlayerA(), game.getPlayerB());
           case DEUCE       -> printer.printDeuce();
           case ADVANTAGE_A -> printer.printAdvantage(game.getPlayerA());
           case ADVANTAGE_B -> printer.printAdvantage(game.getPlayerB());
           case WIN         -> printer.printWinner(winner);
       }
   }


    private Player opponent(Game game, Player player){
        return game.getPlayerA().getName().equals(player.getName()) ? game.getPlayerB() : game.getPlayerA();
    }

}
