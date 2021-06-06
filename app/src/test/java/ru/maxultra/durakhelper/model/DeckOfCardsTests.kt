package ru.maxultra.durakhelper.model

import org.junit.Assert.*
import org.junit.Test

class DeckOfCardsTests {

    @Test
    fun get52CardsTest() {
        val deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.FIFTY_TWO)
        assertEquals(52, deck.size)
        for (suit in Card.Suit.values()) {
            assertTrue(deck.contains(Card(suit, Card.Rank.TWO)))
            assertTrue(deck.contains(Card(suit, Card.Rank.SIX)))
            assertTrue(deck.contains(Card(suit, Card.Rank.SEVEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.NINE)))
            assertTrue(deck.contains(Card(suit, Card.Rank.QUEEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.ACE)))
        }
    }

    @Test
    fun get36CardsTest() {
        val deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_SIX)
        assertEquals(36, deck.size)
        for (suit in Card.Suit.values()) {
            assertFalse(deck.contains(Card(suit, Card.Rank.TWO)))
            assertTrue(deck.contains(Card(suit, Card.Rank.SIX)))
            assertTrue(deck.contains(Card(suit, Card.Rank.SEVEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.NINE)))
            assertTrue(deck.contains(Card(suit, Card.Rank.QUEEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.ACE)))
        }
    }

    @Test
    fun get32CardsTest() {
        val deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_TWO)
        assertEquals(32, deck.size)
        for (suit in Card.Suit.values()) {
            assertFalse(deck.contains(Card(suit, Card.Rank.TWO)))
            assertFalse(deck.contains(Card(suit, Card.Rank.SIX)))
            assertTrue(deck.contains(Card(suit, Card.Rank.SEVEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.NINE)))
            assertTrue(deck.contains(Card(suit, Card.Rank.QUEEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.ACE)))
        }
    }

    @Test
    fun get24CardsTest() {
        val deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.TWENTY_FOUR)
        assertEquals(24, deck.size)
        for (suit in Card.Suit.values()) {
            assertFalse(deck.contains(Card(suit, Card.Rank.TWO)))
            assertFalse(deck.contains(Card(suit, Card.Rank.SIX)))
            assertFalse(deck.contains(Card(suit, Card.Rank.SEVEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.NINE)))
            assertTrue(deck.contains(Card(suit, Card.Rank.QUEEN)))
            assertTrue(deck.contains(Card(suit, Card.Rank.ACE)))
        }
    }
}
