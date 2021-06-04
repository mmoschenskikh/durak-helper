package ru.maxultra.durakhelper.model

object DeckOfCards {
    private val deck: List<Card>

    enum class DeckSize(val asInt: Int) {
        TWENTY_FOUR(24), THIRTY_TWO(32), THIRTY_SIX(36), FIFTY_TWO(52)
    }

    init {
        val deckOfCards = mutableListOf<Card>()
        for (rank in Card.Rank.values()) {
            for (suit in Card.Suit.values()) {
                deckOfCards.add(Card(suit, rank))
            }
        }
        deck = deckOfCards.toList()
    }

    /**
     * Returns the deck of size [size].
     * Note that the deck is a singleton.
     */
    fun getDeckOfSize(size: DeckSize) = deck.subList(0, size.asInt)
}
