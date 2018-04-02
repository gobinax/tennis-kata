package com.arolla.tennis;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.arolla.tennis.Player.ALICE;
import static com.arolla.tennis.Player.BOB;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class GameTest {

    Player A = ALICE;
    Player B = BOB;

    public Object params_keep_score() {
        return new Object[]{
                new Object[]{"0-0", new Player[]{}},

                new Object[]{"15-0", new Player[]{A}},
                new Object[]{"30-0", new Player[]{A, A}},
                new Object[]{"40-0", new Player[]{A, A, A}},
                new Object[]{"Game: ALICE", new Player[]{A, A, A, A}},

                new Object[]{"0-15", new Player[]{B}},
                new Object[]{"0-30", new Player[]{B, B}},
                new Object[]{"0-40", new Player[]{B, B, B}},
                new Object[]{"Game: BOB", new Player[]{B, B, B, B}}
        };
    }

    @Test
    @Parameters(method = "params_keep_score")
    public void should_keep_game_score_from_0_to_win(String expectedPrintedScore, Player... points) {
        // GIVEN
        Game game = new Game(A, B);

        // WHEN
        for (Player player : points) {
            game.point(player);
        }

        // THEN
        assertThat(game.printScore())
                .isEqualTo(expectedPrintedScore);
    }
}