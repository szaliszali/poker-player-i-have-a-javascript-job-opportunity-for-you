package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        try {
            var gameState = new GameState(request);
            
            int currentBuyIn = gameState.currentBuyIn;
            int myPreviousBet = gameState.bet;
            var myHoleCards = gameState.holeCards;
            String myFirstHoleCardRank = myHoleCards.get(0).rank;
            String mySecondHoleCardRank = myHoleCards.get(1).rank;

            int numberOfCommunityCards = gameState.communityCards.size();
            List<Card> allCards = gameState.allCards;
            Hand fullHand = new Hand(allCards);

            if (numberOfCommunityCards > 0) {
                int[] maxSameRanks = fullHand.maxSameRanks();
                int max1 = maxSameRanks[0];
                int max2 = maxSameRanks[1];
                if (max1 >= 4 || max1 == 3 && max2 == 2 || fullHand.isFlush() || fullHand.isStraight()) {
                    return 1000;
                } else if (max1 == 3) {
                    int bet = currentBuyIn-myPreviousBet;
                    int rank = fullHand.analyseRank();
                    if (bet < rank * 20) {
                        bet = rank * 20;
                    }
                    if (rank == 14) {
                        bet = 400;
                    }
                    return bet;
                } else if (max1 == 2 && max2 == 2) {
                    int bet = currentBuyIn-myPreviousBet;
                    if (bet < 200) {
                        bet = 200;
                    }
                    return bet;
                } else if (max1 == 2) {
                    int rank = fullHand.analyseRank();
                    int bet = currentBuyIn-myPreviousBet;
                    if (bet < rank * 10) {
                        bet = rank * 10;
                    }
                    if (rank == 14) {
                        bet = 200;
                    }
                    return bet;
                }
                return 0;
            }

            if (numberOfCommunityCards == 0 && myFirstHoleCardRank.equals("A") && myFirstHoleCardRank.equals(mySecondHoleCardRank) ||
                    myFirstHoleCardRank.equals(mySecondHoleCardRank)) {
                return 400;
            }

            if (numberOfCommunityCards == 0 && myFirstHoleCardRank.equals("K") && myFirstHoleCardRank.equals(mySecondHoleCardRank)) {
                return 300;
            }

            if (numberOfCommunityCards == 0 && (isTheCardFigure(myFirstHoleCardRank) && myFirstHoleCardRank.equals(mySecondHoleCardRank))) {
                return 200;
            }

            if (numberOfCommunityCards == 0 && (isTheCardFigure(myFirstHoleCardRank) || isTheCardFigure(mySecondHoleCardRank)
                   )) {
                return currentBuyIn-myPreviousBet;
            }
            return 0;

        } catch (Exception e) {
            System.err.println("EXCEPTION: " +e.toString());
            return 0;
        }

    }

    public static boolean isTheCardFigure (String rank) {
        return (rank.equals("A") || rank.equals("K") || rank.equals("Q") || rank.equals("J"));
    }

    public static void showdown(JsonElement game) {
    }

//    {
//        "tournament_id":"550d1d68cd7bd10003000003",     // Id of the current tournament
//
//            "game_id":"550da1cb2d909006e90004b1",           // Id of the current sit'n'go game. You can use this to link a
//            // sequence of game states together for logging purposes, or to
//            // make sure that the same strategy is played for an entire game
//
//            "round":0,                                      // Index of the current round within a sit'n'go
//
//            "bet_index":0,                                  // Index of the betting opportunity within a round
//
//            "small_blind": 10,                              // The small blind in the current round. The big blind is twice the
//            //     small blind
//
//            "current_buy_in": 320,                          // The amount of the largest current bet from any one player
//
//            "pot": 400,                                     // The size of the pot (sum of the player bets)
//
//            "minimum_raise": 240,                           // Minimum raise amount. To raise you have to return at least:
//            //     current_buy_in - players[in_action][bet] + minimum_raise
//
//            "dealer": 1,                                    // The index of the player on the dealer button in this round
//            //     The first player is (dealer+1)%(players.length)
//
//            "orbits": 7,                                    // Number of orbits completed. (The number of times the dealer
//            //     button returned to the same player.)
//
//            "in_action": 1,                                 // The index of your player, in the players array
//
//            "players": [                                    // An array of the players. The order stays the same during the
//        {                                           //     entire tournament
//
//            "id": 0,                                // Id of the player (same as the index)
//
//                "name": "Albert",                       // Name specified in the tournament config
//
//                "status": "active",                     // Status of the player:
//                //   - active: the player can make bets, and win the current pot
//                //   - folded: the player folded, and gave up interest in
//                //       the current pot. They can return in the next round.
//                //   - out: the player lost all chips, and is out of this sit'n'go
//
//                "version": "Default random player",     // Version identifier returned by the player
//
//                "stack": 1010,                          // Amount of chips still available for the player. (Not including
//                //     the chips the player bet in this round.)
//
//                "bet": 320                              // The amount of chips the player put into the pot
//        },
//        {
//            "id": 1,                                // Your own player looks similar, with one extension.
//                "name": "Bob",
//                "status": "active",
//                "version": "Default random player",
//                "stack": 1590,
//                "bet": 80,
//                "hole_cards": [                         // The cards of the player. This is only visible for your own player
//            //     except after showdown, when cards revealed are also included.
//            {
//                "rank": "6",                    // Rank of the card. Possible values are numbers 2-10 and J,Q,K,A
//                    "suit": "hearts"                // Suit of the card. Possible values are: clubs,spades,hearts,diamonds
//            },
//            {
//                "rank": "K",
//                    "suit": "spades"
//            }
//            ]
//        },
//        {
//            "id": 2,
//                "name": "Chuck",
//                "status": "out",
//                "version": "Default random player",
//                "stack": 0,
//                "bet": 0
//        }
//    ],
//        "community_cards": [                            // Finally the array of community cards.
//        {
//            "rank": "4",
//                "suit": "spades"
//        },
//        {
//            "rank": "A",
//                "suit": "hearts"
//        },
//        {
//            "rank": "6",
//                "suit": "clubs"
//        }
//    ]
//    }
}
