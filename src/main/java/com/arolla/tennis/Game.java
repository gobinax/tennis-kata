package com.arolla.tennis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Game {

    private final static String[] pointsLabel = {"0", "15", "30", "40"};

    private final List<Player> players;
    private int points[] = {0, 0};

    public Game(Player player1, Player player2) {
        players = Arrays.asList(player1, player2);
    }

    public void point(Player scorer) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this game is already won by " + winner().get());
        }

        int scorerIdx = players.indexOf(scorer);
        if (scorerIdx == -1) {
            throw new IllegalArgumentException(
                    String.format("Game is between %s and %s. %s cannot score point.", players.get(0), players.get(1), scorer)
            );
        }

        increase(scorerIdx);
    }

    public Optional<Player> winner() {
        if (win(0)) {
            return Optional.of(players.get(0));
        }
        if (win(1)) {
            return Optional.of(players.get(1));
        }
        return Optional.empty();
    }


    public String printScore() {
        if (win(0)) {
            return "Game: " + players.get(0);
        }
        if (win(1)) {
            return "Game: " + players.get(1);
        }
        return pointsLabel[points[0]] + "-" + pointsLabel[points[1]];
    }

    private boolean win(int playerIdx) {
        return points[playerIdx] > pointsLabel.length - 1;
    }

    private void increase(int playerIdx) {
        points[playerIdx]++;
    }

}
