package ru.maxultra.durakhelper.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            .height(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier
                .width(64.dp)
                .padding(vertical = 6.dp),
            thickness = 4.dp
        )
    }
}

@Composable
fun TrumpSuitChooser(viewModel: DeckViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "Choose trump suit",
            style = MaterialTheme.typography.subtitle1,
            fontFamily = montserrat,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SuitIconComponent(
                suitIconId = R.drawable.diamonds,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setTrumpSuit(Card.Suit.DIAMONDS) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.clubs,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setTrumpSuit(Card.Suit.CLUBS) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.hearts,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setTrumpSuit(Card.Suit.HEARTS) }
            )
            SuitIconComponent(
                suitIconId = R.drawable.spades,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setTrumpSuit(Card.Suit.SPADES) }
            )
        }
    }
}

@Composable
fun SuitIconComponent(
    @DrawableRes suitIconId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(id = suitIconId),
        contentDescription = null,
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
            .aspectRatio(1f),
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
