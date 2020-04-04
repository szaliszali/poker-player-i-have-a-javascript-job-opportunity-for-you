package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Card {
    public final String rank;
    public final String suit;

    public Card(JsonElement jsonElement) {
        this.rank = jsonElement.getAsJsonObject().get("rank").getAsString();
        this.suit = jsonElement.getAsJsonObject().get("suit").getAsString();
    }

    public int rankValue() {
        return rankValue(this.rank);
    }

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

    public boolean isFigure() {
        return (rank.equals("A") || rank.equals("K") || rank.equals("Q") || rank.equals("J"));
    }
}
