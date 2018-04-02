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
        int winnerIdx = winnerIdx();
        if (winnerIdx != -1) {
            return Optional.of(players.get(winnerIdx));
        }
        return Optional.empty();
    }

    public String printScore() {
        int winnerIdx = winnerIdx();
        if (winnerIdx != -1) {
            return "Game: " + players.get(winnerIdx);
        }

        if (isDeuce()) {
            return "Deuce";
        }

        int advantageIdx = advantageIdx();
        if (advantageIdx != -1) {
            return "Advantage: " + players.get(advantageIdx);
        }

        return pointsLabel[points[0]] + "-" + pointsLabel[points[1]];
    }

    @Override
    public String toString() {
        return printScore();
    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    /**
     * @return 0: player1 wins, 1: player2 wins, -1: no winner yet
     */
    private int winnerIdx() {
        if (isWinner(0)) return 0;
        if (isWinner(1)) return 1;
        return -1;
    }

    /**
     * @return true if playerIdx won this game
     */
    private boolean isWinner(int playerIdx) {
        return haveEnoughPoint(playerIdx) && has2PointsAdvantage(playerIdx);
    }

    private boolean isDeuce() {
        return points[0] >= 3 && points[0] == points[1];
    }

    /**
     * @return 0: player1 has advantage, 1: player2 has advantage, -1: no advantage at the moment
     */
    private int advantageIdx() {
        if (hasAdvantage(0)) return 0;
        if (hasAdvantage(1)) return 1;
        return -1;
    }

    /**
     * @return true if player has advantage on other player
     */
    private boolean hasAdvantage(int playerIdx) {
        return haveEnoughPoint(playerIdx)
                && !has2PointsAdvantage(playerIdx)
                && hasMorePoints(playerIdx);
    }

    /**
     * @return true if player has more points than other player
     */
    private boolean hasMorePoints(int playerIdx) {
        return points[playerIdx] > points[otherPlayerIdx(playerIdx)];
    }

    /**
     * @return true if player has enought point to win the game or being in deuce situation
     */
    private boolean haveEnoughPoint(int playerIdx) {
        return points[playerIdx] > pointsLabel.length - 1;
    }

    /**
     * @return true if player has 2 more points than other player
     */
    private boolean has2PointsAdvantage(int playerIdx) {
        return points[playerIdx] - points[otherPlayerIdx(playerIdx)] > 1;
    }

    /**
     * @return the index of the other player
     */
    private int otherPlayerIdx(int playerIdx) {
        return (playerIdx == 0) ? 1 : 0;
    }

    private void increasePoints(int playerIdx) {
        points[playerIdx]++;
    }

}
