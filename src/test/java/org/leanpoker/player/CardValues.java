package org.leanpoker.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardValues {
    @Test
    public void rankValueTests() {
        Assertions.assertEquals(2, Card.rankValue("2"));
        Assertions.assertEquals(3, Card.rankValue("3"));
        Assertions.assertEquals(11, Card.rankValue("J"));
        Assertions.assertEquals(12, Card.rankValue("Q"));
        Assertions.assertEquals(13, Card.rankValue("K"));
        Assertions.assertEquals(14, Card.rankValue("A"));
    }
}
