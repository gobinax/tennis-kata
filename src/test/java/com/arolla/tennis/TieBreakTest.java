package com.arolla.tennis;

import com.arolla.tennis.game.Game;
import com.arolla.tennis.game.TieBreak;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.arolla.tennis.Player.ALICE;
import static com.arolla.tennis.Player.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class TieBreakTest {

    Player A = ALICE;
    Player B = BOB;

    public Object params_no_winner() {
        return new Object[]{
                new Object[]{"0-0", new Player[]{}},

                new Object[]{"5-0", new Player[]{A, A, A, A, A}},
                new Object[]{"0-5", new Player[]{B, B, B, B, B}},

                new Object[]{"6-5", new Player[]{A, A, A, A, A, B, B, B, B, B, A}},
                new Object[]{"5-6", new Player[]{A, A, A, A, A, B, B, B, B, B, B}},

                new Object[]{"8-7", new Player[]{A, A, A, A, A, B, B, B, B, B, A, B, A, B, A}},
                new Object[]{"7-8", new Player[]{A, A, A, A, A, B, B, B, B, B, A, B, A, B, B}},
        };
    }

    @Test
    @Parameters(method = "params_no_winner")
    public void should_not_detect_winner(String expectedPrintedScore, Player... points) {
        // GIVEN
        Game game = new TieBreak(A, B);

        // WHEN
        for (Player player : points) {
            game.point(player);
        }

        // THEN
        assertThat(game.printScore())
                .isEqualTo(expectedPrintedScore);

        assertThat(game.winner())
                .as("There should not be a winner to this game")
                .isEmpty();
    }


    public Object params_detect_winner() {
        return new Object[]{
                new Object[]{"Game: ALICE", A, new Player[]{A, A}}, // 6-4
                new Object[]{"Game: BOB", B, new Player[]{B, B}}, // 4-6

                new Object[]{"Game: ALICE", A, new Player[]{A, B, A, A}}, // 7-5
                new Object[]{"Game: BOB", B, new Player[]{A, B, B, B}}, // 5-7

                new Object[]{"Game: ALICE", A, new Player[]{A, B, A, B, A, A}}, // 8-6
                new Object[]{"Game: BOB", B, new Player[]{A, B, A, B, B, B}}, // 6-8
        };
    }

    @Test
    @Parameters(method = "params_detect_winner")
    public void should_detect_winner(String expectedPrintedScore, Player expectedWinner, Player... points) {
        // GIVEN
        Game game = new TieBreak(A, B);
        game.point(A).point(A).point(A).point(A);
        game.point(B).point(B).point(B).point(B); // 4-4

        // WHEN
        for (Player player : points) {
            game.point(player);
        }

        // THEN
        assertThat(game.printScore())
                .isEqualTo(expectedPrintedScore);

        assertThat(game.winner())
                .as("The winner is not the expected one.")
                .contains(expectedWinner);

        assertThatThrownBy(() -> game.point(A))
                .as("should not keep playing a game that is already won")
                .isInstanceOf(IllegalStateException.class);
    }

}