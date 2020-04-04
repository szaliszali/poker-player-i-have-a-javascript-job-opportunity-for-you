package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class GameState {
    public final List<Card> holeCards;
    public final List<Card> allCards;

    public GameState(JsonElement jsonElement) {
        holeCards = new LinkedList<>();
        allCards = new LinkedList<>();

        var me = me(jsonElement);
        var myHoleCards = me.get("hole_cards").getAsJsonArray();
        for (var card : myHoleCards) {
            holeCards.add(new Card(card));
            allCards.add(new Card(card));
        }
        var communityCards = jsonElement.getAsJsonObject().get("community_cards").getAsJsonArray();
        for (var card : communityCards) {
            allCards.add(new Card(card));
        }
    }

    JsonObject me(JsonElement jsonElement) {
        var inAction = jsonElement.getAsJsonObject().get("in_action").getAsInt();
        var players = jsonElement.getAsJsonObject().get("players").getAsJsonArray();
        return players.get(inAction).getAsJsonObject();
    }
}
