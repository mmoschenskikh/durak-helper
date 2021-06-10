package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.model.DeckOfCards

@Composable
@ExperimentalMaterialApi
fun DurakHelperScreen(viewModel: DeckViewModel) {
    val exitRequested by viewModel.isExitRequested.observeAsState(false)
    val (resetRequested, setResetRequested) = remember { mutableStateOf(false) }
    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetContent = { BottomSheet(viewModel = viewModel) },
        sheetPeekHeight = 16.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        topBar = { DurakTopAppBar(onResetClick = { setResetRequested(viewModel.isDeckChanged) }) },
        sheetElevation = 8.dp,
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
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
                    // FIXME: Bottom buttons size should be the same for any deck size
                    BottomBarComponent(buttonWidth = w, buttonHeight = h, viewModel = viewModel)
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
}
