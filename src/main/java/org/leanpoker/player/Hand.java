package org.leanpoker.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {

    public List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public int[] maxSameRanks(List<Card> cards) {
        return new Hand(cards).maxSameRanks();
    }

    public int[] maxSameRanks() {
        int[] maximums = new int[2];
        Map<Integer, Integer> cardMap = new HashMap<>();
        for (Card card : cards) {
            int rank = card.rankValue();
            if (cardMap.containsKey(rank)) {
                cardMap.put(rank, cardMap.get(rank));
            } else {
                cardMap.put(rank, 1);
            }
        }
        int maxCount = 0;
        int secondMax = 0;
        for (Map.Entry<Integer, Integer> entry : cardMap.entrySet()) {
            Integer count = entry.getValue();
            if (count >= maxCount) {
                secondMax = maxCount;
                maxCount = count;
            }
        }
        maximums[0] = maxCount;
        maximums[1] = secondMax;
        return maximums;
    }


}
