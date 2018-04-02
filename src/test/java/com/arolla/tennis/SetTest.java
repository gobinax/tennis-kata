package com.arolla.tennis;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

import static com.arolla.tennis.Player.ALICE;
import static com.arolla.tennis.Player.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JUnitParamsRunner.class)
public class SetTest {

    Player A = ALICE;
    Player B = BOB;

    public Object params_no_winner() {
        return new Object[]{
                new Object[]{"0-0", new Player[]{}},

                new Object[]{"1-0", new Player[]{A}},
                new Object[]{"5-0", new Player[]{A, A, A, A, A}},

                new Object[]{"0-1", new Player[]{B}},
                new Object[]{"0-5", new Player[]{B, B, B, B, B}},

                new Object[]{"6-5", new Player[]{B, B, B, B, B, A, A, A, A, A, A}},
                new Object[]{"5-6", new Player[]{B, B, B, B, B, A, A, A, A, A, B}},
        };
    }

    @Test
    @Parameters(method = "params_no_winner")
    public void should_not_detect_winner(String expectedPrintedScore, Player... games) {
        // GIVEN
        Set set = new Set(A, B);

        // WHEN
        for (Player player : games) {
            takeGame(set, player);
        }

        // THEN
        assertThat(set.printScore())
                .isEqualTo(expectedPrintedScore);

        assertThat(set.winner())
                .as("The winner is not the expected one.")
                .isEmpty();
    }


    public Object params_detect_winner() {
        return new Object[]{
                new Object[]{"6-0", A, new Player[]{A, A, A, A, A, A}},
                new Object[]{"0-6", B, new Player[]{B, B, B, B, B, B}},

                new Object[]{"6-4", A, new Player[]{A, A, A, A, B, B, B, B, A, A}},
                new Object[]{"4-6", B, new Player[]{A, A, A, A, B, B, B, B, B, B}},

                new Object[]{"7-5", A, new Player[]{A, A, A, A, A, B, B, B, B, B, A, A}},
                new Object[]{"5-7", B, new Player[]{A, A, A, A, A, B, B, B, B, B, B, B}},
        };
    }

    @Test
    @Parameters(method = "params_detect_winner")
    public void should_detect_winner(String expectedPrintedScore, Player expectedWinner, Player... games) {
        // GIVEN
        Set set = new Set(A, B);

        // WHEN
        for (Player player : games) {
            takeGame(set, player);
        }

        // THEN
        assertThat(set.printScore())
                .isEqualTo(expectedPrintedScore);

        assertThat(set.winner())
                .as("The winner is not the expected one.")
                .contains(expectedWinner);
    }

    @Test
    public void should_not_score_more_point_when_set_is_won() {
        // GIVEN
        Set set = new Set(A, B);

        // WHEN
        doNTimes(6, () -> takeGame(set, A));

        // THEN
        assertThatThrownBy(() -> set.point(A))
                .as("should not keep playing a game that is already won")
                .isInstanceOf(IllegalStateException.class);
    }

    /**
     * for convenience: player scores 4 Aces and take the game
     */
    private Set takeGame(Set set, Player player) {
        return set.point(player).point(player).point(player).point(player);
    }

    private void doNTimes(int until, Callable todo) {
        try {
            for (int i = 0; i < until; i++) {
                todo.call();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}