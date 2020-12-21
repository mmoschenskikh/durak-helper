package ru.maxultra.durakhelper.model

data class Card(val suit: Suit, val rank: Rank) {
    enum class Suit(val suitImage: String) {
        DIAMONDS("♦"), CLUBS("♣"), HEARTS("♥"), SPADES("♠")
    }

    enum class Rank {
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX
    }
}
