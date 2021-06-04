package ru.maxultra.durakhelper.model

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

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

    @Test
    fun deckStateTest() {
        var deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_SIX)
        for (i in 0..10) {
            val randomCard = Random.nextInt(36)
            val randomStatus: Card.Status
            with(Card.Status.values()) {
                randomStatus = this[Random.nextInt(this.size)]
            }

            deck[randomCard].status = randomStatus
            assertEquals(randomStatus, deck[randomCard].status)
            deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.TWENTY_FOUR)
            if (randomCard < 24) {
                assertEquals(randomStatus, deck[randomCard].status)
            }
            deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_TWO)
            if (randomCard < 32) {
                assertEquals(randomStatus, deck[randomCard].status)
            }
            deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.FIFTY_TWO)
            assertEquals(randomStatus, deck[randomCard].status)

            deck = DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_SIX)
            assertEquals(randomStatus, deck[randomCard].status)
        }

    }
}
