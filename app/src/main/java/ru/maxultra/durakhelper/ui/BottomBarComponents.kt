package ru.maxultra.durakhelper.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import ru.maxultra.durakhelper.DeckViewModel
import ru.maxultra.durakhelper.R
import ru.maxultra.durakhelper.model.CardStatus


@Composable
fun BottomBarButton(
    width: Dp,
    height: Dp,
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
            .width(width)
            .requiredHeightIn(0.dp, width)
            .height(height)
            .padding(4.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontFamily = montserrat,
            style = style,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            onTextLayout = onTextLayout
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomBarComponent(
    buttonWidth: Dp,
    buttonHeight: Dp,
    viewModel: DeckViewModel,
    scaffoldState: BottomSheetScaffoldState? = null,
    sheetScope: CoroutineScope? = null
) {
    val typography = MaterialTheme.typography.button
    val (textStyle, updateTextStyle) = remember { mutableStateOf(typography) }
    val onTextLayout: (TextLayoutResult) -> Unit = { textLayoutResult ->
        if (textLayoutResult.didOverflowHeight) {
            updateTextStyle(textStyle.copy(fontSize = textStyle.fontSize * 0.9))
        }
    }
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        @Composable
        fun ReadyBottomBarButton(@StringRes text: Int, color: Color, status: CardStatus) {
            BottomBarButton(
                width = buttonWidth,
                height = buttonHeight,
                text = stringResource(text),
                color = color,
                style = textStyle,
                status = status,
                onTextLayout = onTextLayout
            ) {
                viewModel.onBottomButtonClick(status)
                hideBottomSheet(sheetScope, scaffoldState)
            }
        }
        ReadyBottomBarButton(
            text = R.string.friend_cards_label,
            color = FriendColor,
            status = CardStatus.FRIEND
        )
        ReadyBottomBarButton(
            text = R.string.my_cards_label,
            color = MyColor,
            status = CardStatus.MINE
        )
        ReadyBottomBarButton(
            text = R.string.enemy_cards_label,
            color = EnemyColor,
            status = CardStatus.ENEMY
        )
        ReadyBottomBarButton(
            text = R.string.discard_cards_label,
            color = DiscardColor,
            status = CardStatus.DISCARD
        )
    }
}
