package com.arolla.tennis;

import org.junit.Test;

import java.util.Optional;

import static com.arolla.tennis.Player.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchTest {
    @Test
    public void should_start_match() {
        // GIVEN
        Match match = new Match(ALICE, BOB);

        // WHEN
        match.point(ALICE);

        //THEN
        assertThat(match.winner()).isEmpty();
        assertThat(match.printScore())
                .isEqualTo(
                        "ALICE-BOB" + "\n" +
                                "0-0" + "\n" +
                                "15-0");
    }
}