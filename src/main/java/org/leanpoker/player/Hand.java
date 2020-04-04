package org.leanpoker.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {

    public List<Card> cards;

    public static int maxSameRanks(List<Card> hand) {
        Map<Integer, Integer> cardMap = new HashMap<>();
        for (Card card : hand) {
            int rank = card.rankValue();
            if (cardMap.containsKey(rank)) {
                cardMap.put(rank, cardMap.get(rank));
            } else {
                cardMap.put(rank, 1);
            }
        }
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : cardMap.entrySet()) {
            Integer count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }


}
