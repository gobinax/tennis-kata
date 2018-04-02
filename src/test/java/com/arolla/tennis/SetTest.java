package com.arolla.tennis;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.arolla.tennis.Player.ALICE;
import static com.arolla.tennis.Player.BOB;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SetTest {

    Player A = ALICE;
    Player B = BOB;

    public Object params_keep_score() {
        return new Object[]{
                new Object[]{"0-0", new Player[]{}},

                new Object[]{"1-0", new Player[]{A}},
                new Object[]{"2-0", new Player[]{A, A}},
                new Object[]{"3-0", new Player[]{A, A, A}},
                new Object[]{"4-0", new Player[]{A, A, A, A}},
                new Object[]{"5-0", new Player[]{A, A, A, A, A}},
                new Object[]{"6-0", new Player[]{A, A, A, A, A, A}},

                new Object[]{"0-1", new Player[]{B}},
                new Object[]{"0-2", new Player[]{B, B}},
                new Object[]{"0-3", new Player[]{B, B, B}},
                new Object[]{"0-4", new Player[]{B, B, B, B}},
                new Object[]{"0-5", new Player[]{B, B, B, B, B}},
                new Object[]{"0-6", new Player[]{B, B, B, B, B, B}},
        };
    }

    @Test
    @Parameters(method = "params_keep_score")
    public void should_keep_game_score_from_0_to_6(String expectedPrintedScore, Player... games) {
        // GIVEN
        Set set = new Set(A, B);

        // WHEN
        for (Player player : games) {
            set.point(player).point(player).point(player).point(player); //LOVE GAME for
        }

        // THEN
        assertThat(set.printScore())
                .isEqualTo(expectedPrintedScore);


    }
}