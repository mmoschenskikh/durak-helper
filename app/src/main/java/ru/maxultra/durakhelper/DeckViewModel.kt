package ru.maxultra.durakhelper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.DeckOfCards

class DeckViewModel : ViewModel() {

    val deckSize: Int
        get() = DeckOfCards.deck.size

    val deckLiveData: LiveData<List<Card>> = MutableLiveData(DeckOfCards.deck)
}
