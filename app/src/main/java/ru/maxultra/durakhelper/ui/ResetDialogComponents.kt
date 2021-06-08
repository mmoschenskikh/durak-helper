package ru.maxultra.durakhelper.ui

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.maxultra.durakhelper.R

@Composable
fun ResetDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onYesAction: () -> Unit,
    onCancelAction: () -> Unit = {}
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text(stringResource(R.string.alert_title)) },
            text = { Text(stringResource(R.string.alert_question)) },
            confirmButton = {
                ResetDialogButton(
                    text = R.string.yes_button,
                    onClick = {
                        onYesAction()
                        setShowDialog(false)
                    }
                )
            },
            dismissButton = {
                ResetDialogButton(
                    text = R.string.no_button,
                    onClick = {
                        onCancelAction()
                        setShowDialog(false)
                    }
                )
            }
        )
    }
}

@Composable
fun ResetDialogButton(@StringRes text: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MazarineBlue,
            contentColor = Color.White
        )
    ) {
        Text(stringResource(text))
    }
}
