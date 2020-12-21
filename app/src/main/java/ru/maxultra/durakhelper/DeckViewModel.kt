package ru.maxultra.durakhelper

import androidx.lifecycle.ViewModel
import ru.maxultra.durakhelper.model.DeckOfCards

class DeckViewModel : ViewModel() {

    val deck = DeckOfCards.deck
}
