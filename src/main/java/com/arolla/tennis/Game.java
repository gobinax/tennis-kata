package com.arolla.tennis;

import java.util.Optional;

public class Game {

    private final static String[] points = {"0", "15", "30", "40"};

    private final Player player1;
    private final Player player2;

    private Score score = new Score();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void point(Player scorer) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this game is already won by " + winner().get());
        }

        if (scorer.equals(player1)) {
            score.increaseP1();
        } else if (scorer.equals(player2)) {
            score.increaseP2();
        } else {
            throw new IllegalArgumentException(
                    String.format("Game is between %s and %s. %s cannot score point.", player1, player2, scorer)
            );
        }
    }

    public Optional<Player> winner() {
        if (score.points1 > points.length - 1) {
            return Optional.of(player1);
        }
        if (score.points2 > points.length - 1) {
            return Optional.of(player2);
        }
        return Optional.empty();
    }

    public String printScore() {
        if (score.points1 > points.length - 1) {
            return "Game: " + player1;
        }
        if (score.points2 > points.length - 1) {
            return "Game: " + player2;
        }
        return points[score.points1] + "-" + points[score.points2];
    }

    /**
     * Simple helper class to represent the score
     */
    private static class Score {
        private int points1 = 0;
        private int points2 = 0;

        public void increaseP1() {
            points1++;
        }

        private void increaseP2() {
            points2++;
        }
    }

}
