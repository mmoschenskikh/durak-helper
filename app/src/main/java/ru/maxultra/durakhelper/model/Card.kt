package ru.maxultra.durakhelper.model

data class Card(val suit: Suit, val rank: Rank) {
    enum class Suit(val suitImage: String) {
        DIAMONDS("♦"), CLUBS("♣"), HEARTS("♥"), SPADES("♠")
    }

    enum class Rank {
        SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }
}
