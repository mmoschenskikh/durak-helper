package ru.maxultra.durakhelper

import androidx.compose.runtime.mutableStateOf
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

    private val _trumpSuitLiveData = MutableLiveData<Card.Suit?>(null)
    val trumpSuitLiveData: LiveData<Card.Suit?>
        get() = _trumpSuitLiveData

    private val _isResetRequested = MutableLiveData(false)
    val isResetRequested: LiveData<Boolean>
        get() = _isResetRequested

    private val _isExitRequested = MutableLiveData(false)
    val isExitRequested: LiveData<Boolean>
        get() = _isExitRequested

    private val _isExiting = MutableLiveData(false)
    val isExiting: LiveData<Boolean>
        get() = _isExiting

    val state = Array(DeckOfCards.biggestDeckSize) { mutableStateOf(CardStatus.TABLE) }
    private val isDeckChanged: Boolean
        get() = trumpSuitLiveData.value != null || state.any { it.value != CardStatus.TABLE }

    fun onCardClick(index: Int) {
        state[index].value =
            if (state[index].value == CardStatus.IN_GAME)
                CardStatus.TABLE
            else
                CardStatus.IN_GAME
    }

    fun onBottomButtonClick(status: CardStatus) {
        state.forEach {
            if (it.value == CardStatus.IN_GAME)
                it.value = status
        }
    }

    fun setTrumpSuit(suit: Card.Suit?) {
        _trumpSuitLiveData.value = suit
    }

    fun resetDeckStatus() {
        state.forEach { it.value = CardStatus.TABLE }
        _trumpSuitLiveData.value = null
        cancelResetRequest()
    }

    fun requestReset() {
        if (isDeckChanged)
            _isResetRequested.value = true
    }

    fun cancelResetRequest() {
        _isResetRequested.value = false
    }

    fun requestExit() {
        if (isDeckChanged)
            _isExitRequested.value = true
        else
            exitDurakHelper()
    }

    fun cancelExitRequest() {
        _isExitRequested.value = false
    }

    fun exitDurakHelper() {
        _isExiting.value = true
    }
}
