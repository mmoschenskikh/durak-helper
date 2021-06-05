package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.R
import ru.maxultra.durakhelper.model.Card
import ru.maxultra.durakhelper.model.DeckOfCards


@Composable
fun CardComponent(card: Card, cardWidth: Dp, cardHeight: Dp) {
    if (card.status != Card.Status.DISCARD) {
        val color = when (card.status) {
            Card.Status.TABLE -> TableCardColor
            Card.Status.MINE -> MyColor
            Card.Status.FRIEND -> FriendColor
            Card.Status.ENEMY -> EnemyColor
            else -> InGameColor
        }
        val suitIconId = when (card.suit) {
            Card.Suit.CLUBS -> R.drawable.clubs
            Card.Suit.SPADES -> R.drawable.spades
            Card.Suit.DIAMONDS -> R.drawable.diamonds
            Card.Suit.HEARTS -> R.drawable.hearts
        }
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
                modifier = Modifier.padding(8.dp)
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
    }
}

@Composable
fun CardColumnComponent(
    deck: List<Card>,
    suit: Card.Suit,
    trumpSuit: Card.Suit?,
    cardWidth: Dp,
    cardHeight: Dp
) {
    val backgroundColor = if (trumpSuit == suit) {
        when (trumpSuit) {
            Card.Suit.DIAMONDS -> RedSuitColor
            Card.Suit.HEARTS -> RedSuitColor
            Card.Suit.SPADES -> BlackSuitColor
            Card.Suit.CLUBS -> BlackSuitColor
        }
    } else {
        Color.Transparent
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(backgroundColor)
    ) {
        deck.forEach { card ->
            if (card.suit == suit) CardComponent(
                card = card,
                cardWidth = cardWidth,
                cardHeight = cardHeight
            )
        }
    }
}

@Composable
fun CardGridComponent(deck: List<Card>, trumpSuit: Card.Suit? = null) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(TableColor)
    ) {
        val w = maxWidth / 4
        val h = maxHeight / (deck.size / 4)
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Card.Suit.values().forEach { suit ->
                CardColumnComponent(
                    deck = deck,
                    suit = suit,
                    trumpSuit = trumpSuit,
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
    CardGridComponent(DeckOfCards.getDeckOfSize(DeckOfCards.DeckSize.TWENTY_FOUR))
}
