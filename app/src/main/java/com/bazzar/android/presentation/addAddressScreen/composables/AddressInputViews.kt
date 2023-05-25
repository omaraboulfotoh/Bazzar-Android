package com.bazzar.android.presentation.addAddressScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.Area
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.MapInColumn
import com.bazzar.android.presentation.composables.PickerTextInputField
import com.bazzar.android.presentation.composables.TextInputField
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun AddressInputViews(
    modifier: Modifier = Modifier,
    governments: List<Area>,
    areas: List<Area>,
    userLatLng: LatLng,
    selectedGovernment: Area? = null,
    selectedArea: Area? = null,
    streetName: String? = null,
    jada: String? = null,
    block: String? = null,
    houseNumber: String? = null,
    flatNumber: String? = null,
    notes: String? = null,
    toggleEnabled: Boolean = false,
    onSelectGovernment: (government: Area) -> Unit,
    onSelectArea: (area: Area) -> Unit,
    onStreetNameChangec: (value: String) -> Unit,
    onBlockChanged: (value: String) -> Unit,
    onHouseNumberChanged: (value: String) -> Unit,
    onJadaValueChanged: (value: String) -> Unit,
    onFlatNumberChanged: (value: String) -> Unit,
    onNotesChanged: (value: String) -> Unit,
    onToggleChanged: (value: Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            MapInColumn(
                modifier = Modifier.fillMaxSize(),
                isUserLocationEnabled = false,
                zoomControlsEnabled = false,
                scrollGesturesEnabled = false,
                mapToolbarEnabled = false,
                startLatLng = userLatLng,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.m)
                .padding(top = BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.Start,
        ) {
            PickerTextInputField(
                modifier = Modifier.fillMaxWidth(),
                text = selectedGovernment?.title ?: "",
                label = stringResource(R.string.governorate),
                placeholder = stringResource(id = R.string.select_your_governorate),
                pickerItems = governments,
                selectedItem = selectedGovernment,
                textStyle = BazzarTheme.typography.body2Bold.copy(fontSize = 14.sp),
                textInputBackgroundColor = Color.Transparent,
                onValueChange = { },
                onItemSelection = { onSelectGovernment.invoke(it) },
                labelColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                PickerItemView = { item, onClick ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClick.invoke(item) }
                            .padding(vertical = BazzarTheme.spacing.xs),
                        text = item.title ?: "",
                        style = BazzarTheme.typography.body2,
                        fontSize = 14.sp,
                    )
                }
            )
            PickerTextInputField(
                modifier = Modifier.fillMaxWidth(),
                text = selectedArea?.title ?: "",
                label = stringResource(R.string.area),
                placeholder = stringResource(id = R.string.select_your_area),
                pickerItems = areas,
                selectedItem = selectedArea,
                textStyle = BazzarTheme.typography.body2Bold.copy(fontSize = 14.sp),
                textInputBackgroundColor = Color.Transparent,
                onValueChange = { },
                onItemSelection = { onSelectArea.invoke(it) },
                labelColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                PickerItemView = { item, onClick ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClick.invoke(item) }
                            .padding(vertical = BazzarTheme.spacing.xs),
                        text = item.title ?: "",
                        style = BazzarTheme.typography.body2,
                        fontSize = 14.sp,
                    )
                }
            )

            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = block.orEmpty(),
                label = stringResource(id = R.string.block),
                placeholder = stringResource(id = R.string.block),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onBlockChanged.invoke(it) }

            )
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = streetName.orEmpty(),
                label = stringResource(id = R.string.street),
                placeholder = stringResource(id = R.string.select_your_street_name),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onStreetNameChangec.invoke(it) }
            )
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = jada.orEmpty(),
                label = stringResource(id = R.string.jaddah),
                placeholder = stringResource(id = R.string.optional),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onJadaValueChanged.invoke(it) }
            )
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = houseNumber.orEmpty(),
                label = stringResource(id = R.string.house_number),
                placeholder = stringResource(id = R.string.house_number),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onHouseNumberChanged.invoke(it) }

            )
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = flatNumber.orEmpty(),
                label = stringResource(id = R.string.flat_number),
                placeholder = stringResource(id = R.string.optional),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onFlatNumberChanged.invoke(it) }

            )
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = notes.orEmpty(),
                label = stringResource(id = R.string.notes),
                placeholder = stringResource(id = R.string.optional),
                borderFocusColor = BazzarTheme.colors.primaryButtonColor,
                borderUnFocusColor = BazzarTheme.colors.stroke,
                textStyle = BazzarTheme.typography.body2.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                placeHolderStyle = BazzarTheme.typography.body2.copy(fontSize = 14.sp),
                placeholderColor = BazzarTheme.colors.primaryButtonColor,
                textColor = BazzarTheme.colors.primaryButtonColor,
                onValueChange = { onNotesChanged.invoke(it) }

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.set_default_address),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                        color = colorResource(id = R.color.prussian_blue),
                    )
                )
                Spacer(Modifier.width(103.dp))
                Box(
                    Modifier
                        .width(40.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = if (toggleEnabled) R.color.deep_sky_blue else R.color.light_gray))
                        .clickable { onToggleChanged.invoke(toggleEnabled.not()) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.toggle_circle),
                        contentDescription = null,
                        tint = colorResource(id = if (toggleEnabled) R.color.prussian_blue else R.color.dark_gray),
                        modifier = if (toggleEnabled) Modifier
                            .align(Alignment.CenterEnd)
                            .padding(2.dp) else Modifier
                            .align(Alignment.CenterStart)
                            .padding(2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxs))
        }
    }

}