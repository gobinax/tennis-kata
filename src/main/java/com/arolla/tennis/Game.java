package com.arolla.tennis;

import java.util.Optional;

public class Game {

    private final Player player1;
    private final Player player2;

    public Game(Player player1, Player player2) {

        this.player1 = player1;
        this.player2 = player2;
    }

    public void point(Player player) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this game is over");
        }
    }

    public Optional<Player> winner() {
        return Optional.empty();
    }

    public String printScore() {
        return "15-0";
    }
}
