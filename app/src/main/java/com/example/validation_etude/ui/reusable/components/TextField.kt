package com.example.validation_etude.ui.reusable.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.validation_etude.R
import com.example.validation_etude.ui.reusable.utils.Notice
import com.example.validation_etude.ui.reusable.utils.Result

@Composable
fun ValidatedOutlinedTextField(
    text: String,
    onValid: (String) -> Unit,
    validator: (String) -> Result<Nothing, Notice<Int>>,
    isError: Boolean,
    setError: (Boolean) -> Unit,
    @StringRes labelRes: Int,
    @StringRes placeholderRes: Int,
    modifier: Modifier = Modifier,
    @StringRes supportingTextRes: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focused: Boolean = false
) {
    var inputText by remember { mutableStateOf(text) }
    var errorMessageRes by remember { mutableIntStateOf(R.string.app_name) }
    val focusRequester = remember { FocusRequester() }

    if (focused) focusRequester.requestFocus()

    fun validate(unvalidated: String) {
        val validationResult = validator(unvalidated)
        if (validationResult is Result.Ok) {
            val validated = unvalidated
            setError(false)
            onValid(validated)
        } else {
            setError(true)
            errorMessageRes = (validationResult as Result.Fail).error!!.deliver()!!
        }
    }

    OutlinedTextField(
        modifier = modifier.onFocusChanged { state ->
                if (!state.isFocused) {
                    setError(false)
                    inputText = text
                }
            }
            .padding(horizontal = 4.dp)
            .focusRequester(focusRequester),
        value = inputText,
        onValueChange = {
            inputText = it
            validate(inputText)
        },
        singleLine = true,
        label = {
            Text(
                if (isError) stringResource(id = labelRes) + "*"
                else stringResource(id = labelRes)
            )
        },
        placeholder = { Text(stringResource(id = placeholderRes)) },
        isError = isError,
        keyboardActions = KeyboardActions { validate(inputText) },
        trailingIcon = {
            if (isError) {
                Icon(
                    Icons.Filled.Error,
                    "error",
                    tint = MaterialTheme.colorScheme.error
                )
            } else {
                Icon(Icons.Default.Clear,
                     "clear text",
                     modifier = Modifier.clickable { inputText = "" })
            }
        },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = errorMessageRes),
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                supportingTextRes?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = it)                )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation
    )
}

