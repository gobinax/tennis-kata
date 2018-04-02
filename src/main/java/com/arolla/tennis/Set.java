package com.arolla.tennis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Set {

    ////////////////////////
    // FIELD, CONSTRUCTOR //
    ////////////////////////

    private final List<Player> players;

    private int points[] = {0, 0};

    private Game currentGame;

    public Set(Player player1, Player player2) {
        players = Arrays.asList(player1, player2);
        currentGame = new Game(player1, player2);
    }

    ////////////////
    // PUBLIC API //
    ////////////////

    public Set point(Player player) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this set is over");
        }
        currentGame.point(player);

        currentGame.winner()
                .ifPresent(winner -> {
                    int playerIdx = players.indexOf(winner);
                    increasePoints(playerIdx);
                });

        return this;
    }


    public Optional<Player> winner() {
        return Optional.empty();
    }

    public String printScore() {
        return "" + points[0] + "-" + points[1];
    }

    public Game currentGame() {
        return currentGame;
    }

    @Override
    public String toString() {
        return printScore();
    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    private void increasePoints(int playerIdx) {
        points[playerIdx]++;
        currentGame = new Game(players.get(0), players.get(1));
    }
}
