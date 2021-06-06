package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.model.DeckOfCards

@Composable
fun DurakHelperScreen(viewModel: DeckViewModel) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(TableColor)
    ) {
        val deckSize by viewModel.deckSizeLiveData.observeAsState(DeckOfCards.DeckSize.THIRTY_SIX)
        val w = maxWidth / 4
        val h = maxHeight / (deckSize.asInt / 4 + 1)
        Column(modifier = Modifier.fillMaxSize()) {
            CardGridComponent(cardWidth = w, cardHeight = h, viewModel = viewModel)
            BottomBarComponent(width = w, viewModel = viewModel)
        }
    }
}
