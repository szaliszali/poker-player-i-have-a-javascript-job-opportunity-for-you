package org.leanpoker.player;

public class Card {
    public static int rankValue(String rank) {
        switch (rank) {
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            default:
                return Integer.parseInt(rank);
        }
    }
}
