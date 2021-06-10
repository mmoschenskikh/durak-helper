package ru.maxultra.durakhelper.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.R
import ru.maxultra.durakhelper.model.Card

@Composable
fun BottomSheetPull() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier
                .width(64.dp)
                .padding(vertical = 8.dp),
            thickness = 4.dp
        )
    }
}

@Composable
fun TrumpSuitChooser(viewModel: DeckViewModel) {
    val trumpSuit by viewModel.trumpSuitLiveData.observeAsState(null)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.choose_trump_suit),
                style = MaterialTheme.typography.h6,
                fontFamily = montserrat,
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
            )
            if (trumpSuit != null) {
                Text(
                    text = stringResource(R.string.reset_trump_suit),
                    style = MaterialTheme.typography.subtitle1,
                    color = Naval,
                    fontFamily = montserrat,
                    modifier = Modifier
                        .clickable { viewModel.setTrumpSuit(null) }
                        .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SuitIconComponent(
                suitIconId = R.drawable.diamonds,
                modifier = Modifier.weight(1f),
                suit = Card.Suit.DIAMONDS,
                trumpSuit = trumpSuit,
                onClick = { viewModel.setTrumpSuit(it) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.clubs,
                modifier = Modifier.weight(1f),
                suit = Card.Suit.CLUBS,
                trumpSuit = trumpSuit,
                onClick = { viewModel.setTrumpSuit(it) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.hearts,
                modifier = Modifier.weight(1f),
                suit = Card.Suit.HEARTS,
                trumpSuit = trumpSuit,
                onClick = { viewModel.setTrumpSuit(it) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.spades,
                modifier = Modifier.weight(1f),
                suit = Card.Suit.SPADES,
                trumpSuit = trumpSuit,
                onClick = { viewModel.setTrumpSuit(it) }
            )
        }
    }
}

@Composable
fun SuitIconComponent(
    @DrawableRes suitIconId: Int,
    modifier: Modifier = Modifier,
    suit: Card.Suit,
    trumpSuit: Card.Suit?,
    onClick: (Card.Suit?) -> Unit = {}
) {
    Image(
        painter = painterResource(id = suitIconId),
        contentDescription = null,
        modifier = modifier
            .clickable {
                if (trumpSuit == suit)
                    onClick(null)
                else
                    onClick(suit)
            },
        colorFilter =
        if (trumpSuit == suit || trumpSuit == null)
            null
        else
            ColorFilter.lighting(Color.Black, Color.Black)
    )
}

@Composable
fun BottomSheet(viewModel: DeckViewModel) {
    Column {
        BottomSheetPull()
        TrumpSuitChooser(viewModel)
    }
}

@Composable
@Preview
fun DefaultPreview() {
    BottomSheet(viewModel = DeckViewModel())
}
