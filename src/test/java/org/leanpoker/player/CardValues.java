package org.leanpoker.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardValues {
    @Test
    public void rankValueTests() {
        Assertions.assertEquals(2, Card.rankValue("2"));
        Assertions.assertEquals(3, Card.rankValue("3"));
        Assertions.assertEquals(11, Card.rankValue("J"));
        Assertions.assertEquals(12, Card.rankValue("Q"));
        Assertions.assertEquals(13, Card.rankValue("K"));
        Assertions.assertEquals(14, Card.rankValue("A"));
        Assertions.assertEquals(1, new Card("A", "_").rankValueForLowStraight());
    }

    @Test
    public void TwoPair() {
        var cards = new LinkedList<Card>();
        cards.add(new Card("A", "x"));
        cards.add(new Card("A", "y"));
        cards.add(new Card("2", "z"));
        cards.add(new Card("2", "u"));
        cards.add(new Card("5", "v"));
        assertTrue(new Hand(cards).isTwoPairs());
        assertFalse(new Hand(cards).isFullHouse());
    }

    @Test
    public void FullHouse() {
        var cards = new LinkedList<Card>();
        cards.add(new Card("A", "x"));
        cards.add(new Card("A", "y"));
        cards.add(new Card("2", "z"));
        cards.add(new Card("2", "u"));
        cards.add(new Card("2", "v"));
        assertFalse(new Hand(cards).isTwoPairs());
        assertTrue(new Hand(cards).isFullHouse());
    }

    @Test
    public void Flush() {
        var cards = new LinkedList<Card>();
        cards.add(new Card("A", "x"));
        cards.add(new Card("A", "x"));
        cards.add(new Card("2", "x"));
        cards.add(new Card("2", "x"));
        cards.add(new Card("2", "y"));
        assertFalse(new Hand(cards).isFlush());
        cards.add(new Card("2", "x"));
        assertTrue(new Hand(cards).isFlush());
    }

    @Test
    public void Straight() {
        var cards = new LinkedList<Card>();
        cards.add(new Card("A", "x"));
        cards.add(new Card("K", "x"));
        cards.add(new Card("Q", "x"));
        cards.add(new Card("J", "x"));
        cards.add(new Card("J", "y"));
        assertFalse(new Hand(cards).isStraight());
        cards.add(new Card("10", "y"));
        assertTrue(new Hand(cards).isStraight());
    }

    @Test
    public void LowStraight() {
        var cards = new LinkedList<Card>();
        cards.add(new Card("A", "x"));
        cards.add(new Card("2", "x"));
        cards.add(new Card("3", "x"));
        cards.add(new Card("4", "x"));
        cards.add(new Card("4", "y"));
        assertFalse(new Hand(cards).isStraight());
        cards.add(new Card("5", "y"));
        assertTrue(new Hand(cards).isStraight());
    }
}
