package com.bazzar.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes_MediumX

@Composable
fun TextInputField(
    text: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = BazzarTheme.colors.secondaryText,
    disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
    backgroundColor: Color = Color.Transparent,
    borderFocusColor: Color = BazzarTheme.colors.borderColor,
    borderUnFocusColor: Color = BazzarTheme.colors.borderColor,
    textStyle: TextStyle = BazzarTheme.typography.subtitle1,
    placeHolderStyle: TextStyle = BazzarTheme.typography.subtitle1,
    placeholderColor: Color = BazzarTheme.colors.placeholder,
    labelColor: Color = textColor,
    cursorColor: Color = textColor,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {

    Column {
        DescriptionBody(text = label, color = labelColor)

        Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))

        OutlinedTextField(
            value = text,
            textStyle = textStyle,
            placeholder = {
                MessageBody(
                    placeholder,
                    style = placeHolderStyle,
                    color = placeholderColor
                )
            },
            onValueChange = onValueChange,
            modifier = modifier,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            isError = isError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                disabledTextColor = disabledTextColor,
                focusedBorderColor = borderFocusColor,
                unfocusedBorderColor = borderUnFocusColor,
                backgroundColor = backgroundColor,
                placeholderColor = placeholderColor,
                unfocusedLabelColor = placeholderColor,
                cursorColor = cursorColor
            ),
            shape = Shapes_MediumX,
            readOnly = isReadOnly,
            enabled = isEnabled,
            trailingIcon = trailingIcon
        )
    }
}

@Composable
fun PickerTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    label: String,
    placeholder: String,
    onPick: () -> Unit,
    trailingIcon: ImageVector = Icons.Filled.ArrowDropDown,
    trailingIconTint: Color = BazzarTheme.colors.primaryButtonColor,
) {
    TextInputField(
        text = text,
        label = label,
        onValueChange = {},
        placeholder = placeholder,
        isReadOnly = true,
        disabledTextColor = BazzarTheme.colors.secondaryText,
        isEnabled = false,
        trailingIcon = {
            Icon(imageVector = trailingIcon, contentDescription = "Pick", tint = trailingIconTint)
        },
        modifier = modifier.clickable { onPick() }
    )
}

@Composable
fun <T> PickerTextInputField(
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
    dropDownModifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = BazzarTheme.typography.body2,
    label: String = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    pickerItems: List<T>,
    selectedItem: T? = null,
    itemSpacer: Dp = BazzarTheme.spacing.xxs,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = Color.White,
    textInputBackgroundColor: Color = Color.Black,
    placeholderColor: Color = BazzarTheme.colors.placeholder,
    labelColor: Color = textColor,
    cursorColor: Color = textColor,
    isReadOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    onItemSelection: (item: T) -> Unit,
    PickerItemView: @Composable ColumnScope.(item: T, onClick: (item: T) -> Unit) -> Unit?,
    PreviewItem: @Composable (RowScope.(item: T) -> Unit)? = null,
) {

    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density
    var isDropdownExpanded by remember { mutableStateOf(false) }

    fun collapse() {
        isDropdownExpanded = isDropdownExpanded.not()
    }

    Column(modifier = modifier.clickable { collapse() }) {
        DescriptionBody(text = label, color = labelColor)
        Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))
        Row(
            modifier = childModifier
                .fillMaxWidth()
                .background(BazzarTheme.colors.white, shape = Shapes_MediumX)
                .border(
                    width = 1.dp,
                    color = if (isDropdownExpanded) BazzarTheme.colors.primaryButtonColor else BazzarTheme.colors.stroke,
                    shape = Shapes_MediumX
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = text,
                textStyle = textStyle,
                placeholder = {
                    MessageBody(
                        placeholder,
                        style = BazzarTheme.typography.body2.copy(fontSize = 16.sp),
                        color = placeholderColor
                    )
                },
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                keyboardActions = keyboardActions,
                enabled = isReadOnly,
                singleLine = singleLine,
                isError = isError,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = textColor,
                    backgroundColor = textInputBackgroundColor,
                    placeholderColor = placeholderColor,
                    unfocusedLabelColor = placeholderColor,
                    cursorColor = cursorColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            Row(
                modifier = Modifier.padding(end = BazzarTheme.spacing.m),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (PreviewItem != null && selectedItem != null) {
                    PreviewItem(selectedItem)
                    Spacer(modifier = Modifier.width(BazzarTheme.spacing.xs))
                }
                Image(
                    imageVector =
                    if (isDropdownExpanded) Icons.Filled.KeyboardArrowUp
                    else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(textColor)
                )
            }

            DropdownMenu(
                modifier = dropDownModifier,
                expanded = isDropdownExpanded,
                onDismissRequest = { collapse() },
                content = {
                    LazyColumn(modifier = Modifier
                        .size(width = screenWidth, height = 200.dp)
                        .heightIn(min = 200.dp)
                        .padding(BazzarTheme.spacing.m)
                        .border(width = 1.dp, color = BazzarTheme.colors.stroke, shape = Shapes_MediumX)
                        .padding(BazzarTheme.spacing.m),
                    ) {
                        itemsIndexed(pickerItems) { index, item ->
                            PickerItemView(item) {
                                onItemSelection.invoke(item)
                                collapse()
                            }
                            if (index != pickerItems.lastIndex) Spacer(modifier = Modifier.height(itemSpacer))
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SearchTextInput(
    modifier: Modifier = Modifier,
    childRowModifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
    hint: String = stringResource(id = R.string.search),
    textStyle: TextStyle = LocalTextStyle.current.copy(
        color = Color.Black,
        fontSize = BazzarTheme.typography.subtitle1.fontSize
    ),
    cursorColor: SolidColor = SolidColor(Color.White),
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = cursorColor,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                modifier = childRowModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(
                        text = hint,
                        style = textStyle
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}
