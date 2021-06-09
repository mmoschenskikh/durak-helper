package ru.maxultra.durakhelper.model

object DeckOfCards {
    private val deck: List<Card>

    enum class DeckSize(val asInt: Int) {
        TWENTY_FOUR(24), THIRTY_TWO(32), THIRTY_SIX(36), FIFTY_TWO(52)
    }

    /**
     * Returns the largest deck size value defined in [DeckSize] enumeration.
     */
    val biggestDeckSize: Int by lazy {
        DeckSize.values().maxByOrNull { it.asInt }?.asInt
            ?: throw IllegalStateException("No deck sizes defined.")
    }

    init {
        val deckOfCards = ArrayList<Card>(biggestDeckSize)
        for (rank in Card.Rank.values()) {
            for (suit in Card.Suit.values()) {
                deckOfCards.add(Card(suit, rank))
            }
        }
        deck = deckOfCards.toList()
    }

    /**
     * Returns the deck of size [size].
     */
    fun getDeckOfSize(size: DeckSize) = deck.subList(0, size.asInt)

    fun initializeDeckStatus(): List<CardStatus> {
        val statusList = ArrayList<CardStatus>(biggestDeckSize)
        for (i in 0 until biggestDeckSize) {
            statusList.add(CardStatus.TABLE)
        }
        return statusList.toList()
    }
}
