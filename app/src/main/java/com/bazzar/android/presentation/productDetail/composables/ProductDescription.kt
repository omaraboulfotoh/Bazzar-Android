package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Preview
@Composable
fun ProductDescription(
    isTextExpanded: Boolean = false,
    text: String = "This will display the text content with a \"Read More\" link that can be clicked to expand the text and show the full content. When the text is expanded, the \"Read More\" link changes to \"Read Less\", which can be clicked to collapse the text and show only the truncated content again.\n" +
            "\n", maxLines: Int = 2
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    ) {
        Text(
            text = text,
            maxLines = if (isTextExpanded) Int.MAX_VALUE else maxLines,
            style = MaterialTheme.typography.body1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .padding(start = 16.dp)
                .width(343.dp)
                .height(152.dp),
            color = Color.Black

        )
        if (text.length > maxLines && !isTextExpanded) {
            Text(
                text = "Read More",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { /*isTextExpanded = true*/ }
            )
        } else if (isTextExpanded) {
            Text(
                text = "Read Less",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { /*isTextExpanded = false*/ }
            )
        }
    }
}