package ru.maxultra.durakhelper.model

object DeckOfCards {
    val deck: List<Card>

    init {
        val deckOfCards = mutableListOf<Card>()
        for (rank in Card.Rank.values()) {
            for (suit in Card.Suit.values()) {
                deckOfCards.add(Card(suit, rank))
            }
        }
        deck = deckOfCards.toList()
    }
}
