package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.R
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.CardStatus
import ru.maxultra.durakhelper.model.DeckOfCards


@Composable
fun CardComponent(
    index: Int,
    card: Card,
    status: CardStatus,
    cardWidth: Dp,
    cardHeight: Dp,
    onClick: (Int) -> Unit
) {
    if (status != CardStatus.DISCARD) {
        val color = when (status) {
            CardStatus.TABLE -> TableCardColor
            CardStatus.MINE -> MyColor
            CardStatus.FRIEND -> FriendColor
            CardStatus.ENEMY -> EnemyColor
            else -> InGameColor
        }
        val suitIconId = when (card.suit) {
            Card.Suit.CLUBS -> R.drawable.clubs
            Card.Suit.SPADES -> R.drawable.spades
            Card.Suit.DIAMONDS -> R.drawable.diamonds
            Card.Suit.HEARTS -> R.drawable.hearts
        }
        val fontWeight = if (status == CardStatus.IN_GAME) FontWeight.Bold else FontWeight.Normal
        Surface(
            color = color,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black),
            modifier = Modifier
                .width(cardWidth)
                .requiredHeightIn(0.dp, cardWidth)
                .height(cardHeight)
                .padding(2.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onClick(index) }
                    .padding(8.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val fontSize = minOf(maxWidth, maxHeight) * 0.7f
                    Text(
                        text = stringArrayResource(id = R.array.ranks_array)[card.rank.ordinal],
                        maxLines = 1,
                        fontSize = LocalDensity.current.run { fontSize.toSp() },
                        fontWeight = fontWeight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp)
                    )
                }
                Image(
                    painter = painterResource(id = suitIconId),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .width(cardWidth)
                .requiredHeightIn(0.dp, cardWidth)
                .height(cardHeight)
                .clickable { onClick(index) }
        )
    }
}

@Composable
fun CardColumnComponent(
    viewModel: DeckViewModel,
    suit: Card.Suit,
    cardWidth: Dp,
    cardHeight: Dp
) {
    val trumpSuit by viewModel.trumpSuitLiveData.observeAsState(null)
    val backgroundColor = if (trumpSuit == suit) {
        when (trumpSuit) {
            Card.Suit.DIAMONDS -> RedSuitColor
            Card.Suit.HEARTS -> RedSuitColor
            Card.Suit.SPADES -> BlackSuitColor
            Card.Suit.CLUBS -> BlackSuitColor
            null -> Color.Transparent
        }
    } else {
        Color.Transparent
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(backgroundColor)
    ) {
        val deck by viewModel.deckLiveData.observeAsState(emptyList())
        val deckStatus by viewModel.statusLiveData.observeAsState(emptyList())
        deck.forEachIndexed { index, card ->
            if (card.suit == suit)
                CardComponent(
                    index = index,
                    card = card,
                    status = deckStatus[index],
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    onClick = { viewModel.onCardClick(index) }
                )
        }
    }
}

@Composable
fun CardGridComponent(viewModel: DeckViewModel) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(TableColor)
    ) {
        val deckSize by viewModel.deckSizeLiveData.observeAsState(DeckOfCards.DeckSize.THIRTY_SIX)
        val w = maxWidth / 4
        val h = maxHeight / (deckSize.asInt / 4)
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Card.Suit.values().forEach { suit ->
                CardColumnComponent(
                    viewModel = viewModel,
                    suit = suit,
                    cardWidth = w,
                    cardHeight = h
                )
            }
        }
    }
}


@Composable
@Preview
fun DefaultPreview() {
//    CardGridComponent(DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.TWENTY_FOUR), {})
}
