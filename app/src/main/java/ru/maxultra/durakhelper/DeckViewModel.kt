package ru.maxultra.durakhelper

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.DeckOfCards

class DeckViewModel : ViewModel() {
    // TODO: Deck size settings
    val deckSize: Int
        get() = 36

    val deckLiveData: MutableLiveData<List<Card>> =
        MutableLiveData(DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_SIX))

    fun onCardClick(card: Card) {
        card.status = if (card.status == Card.Status.IN_GAME)
            Card.Status.TABLE
        else
            Card.Status.IN_GAME
        deckLiveData.value = deckLiveData.value ?: emptyList()
        Log.d("DeckViewModel", deckLiveData.value?.map { it.status }?.joinToString() ?: "")
    }
}
