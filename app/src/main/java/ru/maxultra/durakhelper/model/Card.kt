package ru.maxultra.durakhelper.model

data class Card(val suit: Suit, val rank: Rank, var status: Status = Status.TABLE) {
    enum class Suit(val suitString: String) {
        DIAMONDS("♦"), CLUBS("♣"), HEARTS("♥"), SPADES("♠")
    }

    enum class Rank(val rankString: String) {
        ACE("A"), KING("K"), QUEEN("Q"), JACK("J"),
        TEN("10"), NINE("9"), EIGHT("8"), SEVEN("7"), SIX("6")
    }

    /**
     * Describes who owns the card
     */
    enum class Status {
        TABLE, MINE, FRIEND, ENEMY, INGAME, DISCARD
    }

    override fun toString(): String {
        return rank.rankString + suit.suitString
    }
}
