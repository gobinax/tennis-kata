package com.arolla.tennis;

public class TennisDemo {

    private static Player A = Player.ALICE;
    private static Player B = Player.BOB;


    public static void main(String[] args) {
        Match match = new Match(A, B);

        int countPoints = 0;
        while (!match.winner().isPresent()) {
            match.point(randomPlayer());
            if (++countPoints % 10 == 0) {
                System.out.println(match.printScore());
            }
        }

        System.out.println(match.printScore());
    }


    private static Player randomPlayer() {
        return (Math.random() <= 0.5) ? A : B;
    }


}
