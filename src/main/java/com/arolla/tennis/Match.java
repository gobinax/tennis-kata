package com.arolla.tennis;

import java.util.Optional;

public class Match {

    private final Player player1;
    private final Player player2;

    private final Set currentSet;


    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentSet = new Set(player1, player2);
    }

    public Match point(Player player) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this match is over");
        }

        currentSet.point(player);
        return this;
    }

    public Optional<Player> winner() {
        return currentSet.winner();
    }

    public String printScore() {
        return String.valueOf(player1) + "-" + player2 + "\n" + currentSet.printScore();
    }

}
