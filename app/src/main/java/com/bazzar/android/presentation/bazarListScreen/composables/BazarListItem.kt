package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.BazaarModel
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BazarListItem(
    modifier: Modifier = Modifier,
    item: BazaarModel,
    showLike: Boolean = true,
    onClick: () -> Unit,
    onFavClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier.width(140.dp),
    ) {
        Column(
            modifier = Modifier
                .width(136.dp)
                .height(200.dp)
                .align(Alignment.Center)
                .background(
                    color = BazzarTheme.colors.primaryButtonColor,
                    shape = CircleShape.copy(
                        bottomStart = CornerSize(16.dp),
                        bottomEnd = CornerSize(16.dp)
                    )
                )
                .clickable { onClick.invoke() }
                .padding(BazzarTheme.spacing.xxs),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            RemoteImage(
                modifier = Modifier
                    .size(126.dp)
                    .clip(CircleShape)
                    .background(BazzarTheme.colors.white),
                contentScale = ContentScale.Crop,
                imageUrl = item.imagePath
            )
            Text(
                modifier = Modifier.fillMaxSize(),
                text = item.name ?: "",
                textAlign = TextAlign.Center,
                style = BazzarTheme.typography.body2Medium,
                color = BazzarTheme.colors.white,
            )
        }

        if (showLike)
            Box(modifier = Modifier
                .align(Alignment.TopEnd)
                .defaultMinSize(minWidth = 40.dp, minHeight = 40.dp)
                .clickable { onFavClicked(item.id.orZero()) }) {
                Image(
                    modifier = Modifier.align(Alignment.TopEnd),
                    painter = painterResource(
                        id = if (item.isWishList.orFalse())
                            R.drawable.ic_fav_active else R.drawable.ic_fav_product
                    ),
                    contentDescription = null,
                )
            }
    }
}