package com.bazzar.android.presentation.bazarDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
import com.bazzar.android.presentation.bazarListScreen.BazarListContract
import com.bazzar.android.presentation.common.gridItems
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.SearchTextInput
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.homeScreen.composables.IndicatorHomeImageSlider
import com.bazzar.android.presentation.productsList.ProductContract
import com.bazzar.android.presentation.productsList.composables.BrandImage
import com.bazzar.android.presentation.productsList.composables.SubCategorySlider
import com.bazzar.android.presentation.productsList.composables.SubCategoryTextSlider
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun BazarDetailScreenContent(
    state: BazarDetailContract.State,
    onSendEvent: (BazarDetailContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(title = state.bazaar?.name.orEmpty(),
            onNavigationClick = { onSendEvent(BazarDetailContract.Event.OnBackIconClicked) })
        // show brands
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
        ) {
            item {
                SearchTextInput(modifier = Modifier
                    .padding(top = BazzarTheme.spacing.l)
                    .padding(horizontal = BazzarTheme.spacing.m)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.stroke,
                        shape = RoundedCornerShape(24.dp)
                    ),
                    childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.m),
                    hint = stringResource(id = R.string.product_search_title),
                    value = state.searchTerm ?: "",
                    onValueChange = { onSendEvent(BazarDetailContract.Event.OnSearchTermChanged(it)) },
                    textStyle = BazzarTheme.typography.body2.copy(color = BazzarTheme.colors.primaryButtonColor),
                    cursorColor = SolidColor(BazzarTheme.colors.primaryButtonColor),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.padding(horizontal = BazzarTheme.spacing.s),
                            painter = painterResource(id = R.drawable.search_icon),
                            tint = BazzarTheme.colors.primaryButtonColor,
                            contentDescription = ""
                        )
                    })
            }
            if (state.slider.size > 1) item {
                IndicatorHomeImageSlider(imagePathList = state.slider.mapNotNull {
                    it.imagePath ?: ""
                }, modifier = Modifier.wrapContentHeight(), onSliderClicked = {
                    onSendEvent(BazarDetailContract.Event.OnSliderClicked(it))
                })
            }
            if (state.categoriesList.size > 1) item {
                SubCategoryTextSlider(modifier = Modifier
                    .background(BazzarTheme.colors.white)
                    .fillMaxWidth()
                    .height(44.dp),
                    subCategoryList = state.categoriesList,
                    onClickSubCategory = { categoryIndex ->
                        onSendEvent(
                            BazarDetailContract.Event.OnSubCategoryClicked(categoryIndex)
                        )
                    })
            }
            gridItems(count = state.productList.orEmpty().size,
                nColumns = 2,
                horizontalArrangement = Arrangement.Start,
                itemContent = { index ->
                    val item = state.productList.orEmpty()[index]
                    ProductItem(item, modifier = Modifier.padding(BazzarTheme.spacing.xs)) {
                        onSendEvent(BazarDetailContract.Event.OnProductClicked(index))
                    }
                })
            item {
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
            }
        }
    }
}









