package com.arolla.tennis;

import java.util.Optional;

public class Set {

    private int gamesP1 = 0;
    private int gamesP2 = 0;

    private Game currentGame;

    public Set(Player player1, Player player2) {
        currentGame = new Game(player1, player2);
    }

    public void point(Player player) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this set is over");
        }
        currentGame.point(player);
    }

    public Optional<Player> winner() {
        return Optional.empty();
    }

    public String printScore() {
        return "" + gamesP1 + "-" + gamesP2 + "\n" + currentGame.printScore();
    }

}
