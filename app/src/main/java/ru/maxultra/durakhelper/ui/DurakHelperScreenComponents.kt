package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.model.DeckOfCards

@Composable
@ExperimentalMaterialApi
fun DurakHelperScreen(viewModel: DeckViewModel) {
    val resetRequested by viewModel.isResetRequested.observeAsState(false)
    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { DurakTopAppBar(onResetClick = { viewModel.requestReset() }) },
        sheetContent = { BottomSheet(viewModel = viewModel) },
        sheetPeekHeight = 20.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 16.dp,
        scaffoldState = scaffoldState,
        backgroundColor = TableColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val deckSize by viewModel.deckSizeLiveData.observeAsState(DeckOfCards.DeckSize.THIRTY_SIX)
                val w = maxWidth / 4
                val h = maxHeight / (deckSize.asInt / 4 + 1)
                Column(modifier = Modifier.fillMaxSize()) {
                    CardGridComponent(
                        cardWidth = w,
                        cardHeight = h,
                        viewModel = viewModel,
                        scaffoldState = scaffoldState,
                        sheetScope = coroutineScope
                    )
                    // FIXME: Bottom buttons size should be the same for any deck size
                    BottomBarComponent(
                        buttonWidth = w,
                        buttonHeight = h,
                        viewModel = viewModel,
                        scaffoldState = scaffoldState,
                        sheetScope = coroutineScope
                    )
                }
            }
            val exitRequested by viewModel.isExitRequested.observeAsState(false)
            ResetDialog(
                showDialog = resetRequested || exitRequested,
                hideDialog = {
                    viewModel.cancelResetRequest()
                    viewModel.cancelExitRequest()
                },
                onYesAction = {
                    if (exitRequested) {
                        viewModel.exitDurakHelper()
                    } else {
                        viewModel.resetDeckStatus()
                        hideBottomSheet(coroutineScope, scaffoldState)
                    }
                }
            )
        }
    }
}
