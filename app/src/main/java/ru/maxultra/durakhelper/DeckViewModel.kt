package ru.maxultra.durakhelper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.CardStatus
import ru.maxultra.durakhelper.model.DeckOfCards

class DeckViewModel : ViewModel() {
    // TODO: Deck size settings
    private val _deckSizeLiveData = MutableLiveData(DeckOfCards.DeckSize.THIRTY_SIX)
    val deckSizeLiveData: LiveData<DeckOfCards.DeckSize>
        get() = _deckSizeLiveData

    val deckLiveData = Transformations.map(deckSizeLiveData) { DeckOfCards.getDeckOfSize(it) }

    private val _statusLiveData = MutableLiveData(DeckOfCards.initializeDeckStatus())
    val statusLiveData: LiveData<List<CardStatus>>
        get() = _statusLiveData

    private val _trumpSuitLiveData = MutableLiveData<Card.Suit?>(null)
    val trumpSuitLiveData: LiveData<Card.Suit?>
        get() = _trumpSuitLiveData

    fun onCardClick(index: Int) {
        _statusLiveData.value?.let { deckStatus ->
            val newStatus = if (deckStatus[index] == CardStatus.IN_GAME)
                CardStatus.TABLE
            else
                CardStatus.IN_GAME
            _statusLiveData.value = deckStatus.mapIndexed { i, oldStatus ->
                if (i == index) newStatus else oldStatus
            }
        }
    }

    fun onBottomButtonClick(status: CardStatus) {
        _statusLiveData.value?.let { deckStatus ->
            _statusLiveData.value = deckStatus.map { oldStatus ->
                if (oldStatus == CardStatus.IN_GAME) status else oldStatus
            }
        }
    }

    fun resetDeckStatus() {
        _statusLiveData.value?.let { deckStatus ->
            _statusLiveData.value = deckStatus.map { CardStatus.TABLE }
        }
        _trumpSuitLiveData.value = null
    }
}
