package com.bazzar.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun DefaultMessageDialog(
    title: String,
    body: String,
    buttonText: String,
    onNegative: () -> Unit,
    onPositive: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    bodyColor: Color = Color.White,
    backgroundColor: Color = BazzarTheme.colors.primaryButtonColor
) {
    Dialog(
        onDismissRequest = onNegative,
        properties = DialogProperties(dismissOnBackPress, dismissOnClickOutside)
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            backgroundColor = backgroundColor,
        ) {

            Column(
                modifier = Modifier.padding(BazzarTheme.spacing.primaryPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.l)
            ) {
                Title(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = BazzarTheme.colors.primaryButtonColor
                )

                MessageBody(
                    text = body,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = bodyColor
                )

                PrimaryButton(
                    text = buttonText,
                    onClick = onPositive,
                    textColor = BazzarTheme.colors.primaryButtonColor,
                    background = BazzarTheme.colors.white
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultMessageDialogPreview() =
    DefaultMessageDialog(
        title = "Title",
        body = "Body",
        buttonText = "Got it",
        onNegative = {},
        onPositive = {}
    )


@Composable
fun ConfirmationMessageDialog(
    title: String,
    body: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onNegative: () -> Unit,
    onPositive: () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.background,
    icon: ImageVector? = null,
) {
    Dialog(onDismissRequest = onNegative) {
        Card(
            shape = MaterialTheme.shapes.large,
            backgroundColor = backgroundColor,
        ) {

            Column(
                modifier = Modifier.padding(BazzarTheme.spacing.primaryPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.l)
            ) {

                Title(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                )

                MessageBody(
                    text = body,
                    modifier = Modifier.fillMaxWidth(),
                )


                Row(
                    horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = BazzarTheme.spacing.s),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        PrimaryOutlinedButton(
                            stroke = BazzarTheme.colors.white,
                            text = negativeButtonText,
                            onClick = onNegative
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        PrimaryButton(
                            text = positiveButtonText,
                            onClick = onPositive,
                            background = BazzarTheme.colors.white,
                            textColor = BazzarTheme.colors.primaryButtonColor,
                            icon = icon
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TwoButtonPreview() =
    ConfirmationMessageDialog(
        title = "Title",
        body = "Body",
        negativeButtonText = "Got it",
        onNegative = {},
        onPositive = {},
        positiveButtonText = "close"
    )
