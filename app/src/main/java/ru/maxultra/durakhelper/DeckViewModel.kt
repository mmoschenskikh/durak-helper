package ru.maxultra.durakhelper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.DeckOfCards

class DeckViewModel : ViewModel() {
    // TODO: Deck size settings
    val deckSize: Int
        get() = 36

    val deckLiveData: LiveData<List<Card>> =
        MutableLiveData(DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.THIRTY_SIX))
}
