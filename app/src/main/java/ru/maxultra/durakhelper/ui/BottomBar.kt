package ru.maxultra.durakhelper.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.R
import ru.maxultra.durakhelper.model.CardStatus


@Composable
fun BottomBarButton(
    width: Dp,
    text: String,
    color: Color,
    style: TextStyle,
    status: CardStatus,
    onTextLayout: (TextLayoutResult) -> Unit,
    onClick: (CardStatus) -> Unit
) {
    TextButton(
        onClick = { onClick(status) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = color,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .padding(4.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontFamily = FontFamily(Font(R.font.montserrat)),
            style = style,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            onTextLayout = onTextLayout
        )
    }
}

@Composable
fun BottomBarComponent(width: Dp, viewModel: DeckViewModel) {
    val typography = MaterialTheme.typography.button
    val (textStyle, updateTextStyle) = remember { mutableStateOf(typography) }
    val onTextLayout: (TextLayoutResult) -> Unit = { textLayoutResult ->
        if (textLayoutResult.didOverflowHeight) {
            updateTextStyle(textStyle.copy(fontSize = textStyle.fontSize * 0.9))
        }
    }
    Row(modifier = Modifier.fillMaxSize()) {
        BottomBarButton(
            width = width,
            text = stringResource(id = R.string.friend_cards_label),
            color = FriendColor,
            status = CardStatus.FRIEND,
            style = textStyle,
            onTextLayout = onTextLayout
        ) {}
        BottomBarButton(
            width = width,
            text = stringResource(id = R.string.my_cards_label),
            color = MyColor,
            status = CardStatus.MINE,
            style = textStyle,
            onTextLayout = onTextLayout
        ) {}
        BottomBarButton(
            width = width,
            text = stringResource(id = R.string.enemy_cards_label),
            color = EnemyColor,
            status = CardStatus.ENEMY,
            style = textStyle,
            onTextLayout = onTextLayout
        ) {}
        BottomBarButton(
            width = width,
            text = stringResource(id = R.string.discard_cards_label),
            color = DiscardColor,
            status = CardStatus.DISCARD,
            style = textStyle,
            onTextLayout = onTextLayout
        ) {}
    }
}
