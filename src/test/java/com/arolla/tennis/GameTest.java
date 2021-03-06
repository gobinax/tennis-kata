package com.arolla.tennis;

import com.arolla.tennis.game.Game;
import com.arolla.tennis.game.RegularGame;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.arolla.tennis.Player.ALICE;
import static com.arolla.tennis.Player.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Game game = new RegularGame(A, B);

        // WHEN
        for (Player player : points) {
            game.point(player);
        }

        // THEN
        assertThat(game.printScore())
                .isEqualTo(expectedPrintedScore);
    }

    @Test
    public void should_not_score_more_point_when_game_is_won() {
        // GIVEN
        Game game = new RegularGame(A, B);

        // WHEN
        game.point(A);
        game.point(A);
        game.point(A);
        game.point(A); // WIN

        // THEN
        assertThatThrownBy(() -> game.point(A))
                .as("should not keep playing a game that is already won")
                .isInstanceOf(IllegalStateException.class);
    }

    public Object params_deuce_rule() {
        return new Object[]{
                new Object[]{"Deuce", new Player[]{}},
                new Object[]{"Deuce", new Player[]{A, B}},

                new Object[]{"Advantage: ALICE", new Player[]{A}},
                new Object[]{"Advantage: ALICE", new Player[]{A, B, A}},
                new Object[]{"Advantage: ALICE", new Player[]{B, A, A}},

                new Object[]{"Advantage: BOB", new Player[]{B}},
                new Object[]{"Advantage: BOB", new Player[]{B, A, B}},
                new Object[]{"Advantage: BOB", new Player[]{A, B, B}},

                new Object[]{"Game: ALICE", new Player[]{A, A}},
                new Object[]{"Game: ALICE", new Player[]{B, A, A, A}},
                new Object[]{"Game: BOB", new Player[]{B, B}},
                new Object[]{"Game: BOB", new Player[]{A, B, B, B}},
        };
    }

    @Test
    @Parameters(method = "params_deuce_rule")
    public void should_handle_deuce_rule(String expectedPrintedScore, Player... points) {
        // GIVEN
        Game game = new RegularGame(A, B);
        game.point(A).point(A).point(A);
        game.point(B).point(B).point(B); //DEUCE

        // WHEN
        for (Player player : points) {
            game.point(player);
        }

        // THEN
        assertThat(game.printScore())
                .isEqualTo(expectedPrintedScore);
    }
}