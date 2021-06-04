package ru.maxultra.durakhelper.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CardTests {
    @Test
    fun toStringTest() {
        assertEquals("10♥", Card(Card.Suit.HEARTS, Card.Rank.TEN).toString())
        assertEquals("2♣", Card(Card.Suit.CLUBS, Card.Rank.TWO).toString())
        assertEquals("5♠", Card(Card.Suit.SPADES, Card.Rank.FIVE).toString())
        assertEquals("A♦", Card(Card.Suit.DIAMONDS, Card.Rank.ACE).toString())
        assertEquals("J♥", Card(Card.Suit.HEARTS, Card.Rank.JACK).toString())
    }
}
