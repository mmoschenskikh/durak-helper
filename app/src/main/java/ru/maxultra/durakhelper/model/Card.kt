package ru.maxultra.durakhelper.model

data class Card(val suit: Suit, val rank: Rank, var status: Status = Status.TABLE) {
    enum class Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES
    }

    enum class Rank {
        ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
    }

    /**
     * Describes who owns the card
     */
    enum class Status {
        TABLE, MINE, FRIEND, ENEMY, IN_GAME, DISCARD
    }

    override fun toString(): String {
        val rankString = when (rank.ordinal) {
            0 -> "A"
            1 -> "K"
            2 -> "Q"
            3 -> "J"
            else -> (Rank.values().size - rank.ordinal + 1).toString()
        }
        val suitString = when (suit) {
            Suit.DIAMONDS -> "♦"
            Suit.CLUBS -> "♣"
            Suit.HEARTS -> "♥"
            Suit.SPADES -> "♠"
        }
        return rankString + suitString
    }
}
