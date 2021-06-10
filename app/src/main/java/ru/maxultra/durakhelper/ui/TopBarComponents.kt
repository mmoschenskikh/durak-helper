package ru.maxultra.durakhelper.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.maxultra.durakhelper.R

@Composable
fun DurakTopAppBar(onResetClick: () -> Unit) {
    TopAppBar(
        title = { TopAppBarTitle() },
        actions = {
            Icon(
                imageVector = MenuAction.Restart.icon,
                contentDescription = stringResource(MenuAction.Restart.label),
                modifier = Modifier
                    .fillMaxHeight(0.67f)
                    .aspectRatio(1f)
                    .clickable { onResetClick() }
            )
        },
        backgroundColor = MazarineBlue,
        contentColor = Color.White
    )
}


@Composable
fun TopAppBarTitle() {
    Text(text = stringResource(R.string.app_name))
}

sealed class MenuAction(@StringRes val label: Int, val icon: ImageVector) {
    object Restart : MenuAction(R.string.new_game_button, Icons.Default.RestartAlt)
}

