package com.arolla.tennis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Game {

    ////////////////////////
    // FIELD, CONSTRUCTOR //
    ////////////////////////

    private final static String[] pointsLabel = {"0", "15", "30", "40"};

    private final List<Player> players;
    private int points[] = {0, 0};

    public Game(Player player1, Player player2) {
        players = Arrays.asList(player1, player2);
    }

    ////////////////
    // PUBLIC API //
    ////////////////

    public Game point(Player scorer) {
        if (winner().isPresent()) {
            throw new IllegalStateException("Cannot play more point: this game is already won by " + winner().get());
        }

        int scorerIdx = players.indexOf(scorer);
        if (scorerIdx == -1) {
            throw new IllegalArgumentException(
                    String.format("Game is between %s and %s. %s cannot score point.", players.get(0), players.get(1), scorer)
            );
        }

        increasePoints(scorerIdx);
        return this;
    }

    public Optional<Player> winner() {
        if (isWinner(0)) {
            return Optional.of(players.get(0));
        }
        if (isWinner(1)) {
            return Optional.of(players.get(1));
        }
        return Optional.empty();
    }


    public String printScore() {
        if (isWinner(0)) {
            return "Game: " + players.get(0);
        }
        if (isWinner(1)) {
            return "Game: " + players.get(1);
        }
        if (isDeuce()) {
            return "Deuce";
        }
        if (hasAdvantage(0)) {
            return "Advantage: " + players.get(0);
        }
        if (hasAdvantage(1)) {
            return "Advantage: " + players.get(1);
        }
        return pointsLabel[points[0]] + "-" + pointsLabel[points[1]];
    }


    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    private boolean isWinner(int playerIdx) {
        return haveEnoughPoint(playerIdx) && has2PointsAdvantage(playerIdx);
    }

    private boolean isDeuce() {
        return points[0] >= 3 && points[0] == points[1];
    }

    private boolean hasAdvantage(int playerIdx) {
        return haveEnoughPoint(playerIdx)
                && !has2PointsAdvantage(playerIdx)
                && hasMorePoints(playerIdx);
    }

    private boolean hasMorePoints(int playerIdx) {
        return points[playerIdx] > points[otherPlayerIdx(playerIdx)];
    }

    private boolean haveEnoughPoint(int playerIdx) {
        return points[playerIdx] > pointsLabel.length - 1;
    }

    private boolean has2PointsAdvantage(int playerIdx) {
        return points[playerIdx] - points[otherPlayerIdx(playerIdx)] > 1;
    }

    private int otherPlayerIdx(int playerIdx) {
        return (playerIdx == 0) ? 1 : 0;
    }

    private void increasePoints(int playerIdx) {
        points[playerIdx]++;
    }

}
