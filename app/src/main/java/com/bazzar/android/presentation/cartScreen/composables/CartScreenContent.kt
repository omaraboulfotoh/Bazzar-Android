package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.presentation.home_screen.composables.ProductsGroup
import com.bazzar.android.presentation.theme.BazzarTheme

val productList = listOf<Product>(
    Product(
        brandTitle = "BrandTitle",
        title = "ProductTitle",
        price = 20050.0,
    ),

    Product(
        brandTitle = "BrandTitle",
        title = "ProductTitle",
        price = 20050.0,
    ),

    Product(
        brandTitle = "BrandTitle",
        title = "ProductTitle",
        price = 20050.0,
    ),
)

@Preview
@Composable
fun CartScreenContent(
    /*
        state: CartContract.State,
    */
    /*onSendEvent: (CartContract.Event) -> Unit*/
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.l)
    ) {
        item {
            CartSummary()
        }

        itemsIndexed(/*state.productCartList*/productList) { index, product ->
            ProductCartItem(/*state?.productCartList*/product)
        }
    }
}


