package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.model.DeckOfCards

@Composable
fun DurakHelperScreen(viewModel: DeckViewModel) {
    val deckIsChanged by viewModel.isDeckChanged.observeAsState(false)
    val exitRequested by viewModel.isExitRequested.observeAsState(false)
    val (resetRequested, setResetRequested) = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        DurakTopAppBar(onResetClick = { setResetRequested(deckIsChanged) })
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
        ResetDialog(
            showDialog = resetRequested || exitRequested,
            setShowDialog = { setResetRequested(it) },
            onYesAction = {
                if (exitRequested) {
                    viewModel.exitDurakHelper()
                } else {
                    viewModel.resetDeckStatus()
                }
            },
            onCancelAction = { viewModel.cancelExitRequest() }
        )
    }
}
