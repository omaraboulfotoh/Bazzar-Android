package com.bazzar.android.presentation.productDetail.composables

import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.OverLine
import com.bazzar.android.presentation.composables.Subtitle
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun ProductDescription(
    modifier: Modifier = Modifier,
    isTextExpanded: Boolean,
    text: String,
    sku: String,
    maxLines: Int = 2,
    onReadMoreLessClicked: () -> Unit,
) {
    Card(
        modifier = modifier.padding(top = BazzarTheme.spacing.m),
        backgroundColor = BazzarTheme.colors.white,
        shape = Shapes.large,
        elevation = BazzarTheme.spacing.xxs
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.Start
        ) {
            // top title

            Subtitle(
                text = stringResource(id = R.string.product_description),
                color = BazzarTheme.colors.black,
                textAlign = TextAlign.Start,
                style = BazzarTheme.typography.subtitle1Bold
            )
            // SKU

            Card(
                modifier = modifier
                    .wrapContentSize()
                    .defaultMinSize(minHeight = 14.dp),
                backgroundColor = BazzarTheme.colors.white,
                shape = Shapes.medium,
                border = BorderStroke(width = 1.dp, color = BazzarTheme.colors.stroke)
            ) {
                OverLine(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(BazzarTheme.spacing.extra_xxs),
                    text = stringResource(id = R.string.sku_format, sku),
                    color = BazzarTheme.colors.textGray,
                    style = BazzarTheme.typography.overlineBold
                )
            }

            // product desc
            HtmlText(
                html = text, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            // show more and less
            Text(text = stringResource(
                id = if (text.length > maxLines && !isTextExpanded)
                    R.string.read_more else R.string.read_less
            ), style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onReadMoreLessClicked() })
        }
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier,
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}