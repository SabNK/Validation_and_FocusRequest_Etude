package ru.polescanner.droidmvp.ui.reusablecomponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxWithText(
    //ToDo maybe UiText
    label: String?,
    uiState: Boolean?,
    onClick: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
        ) {
        TriStateCheckbox(
            state = uiState.toToggleableState(),
            onClick = { onClick(uiState.nextState()) }
        )
        label?.let {
            Text(
                text = it,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

private fun Boolean?.toToggleableState() =
    this?. let {
        if (it) ToggleableState.On
        else ToggleableState.Off
    } ?: ToggleableState.Indeterminate

private fun Boolean?.nextState() =
    this?.let{ !it } ?: true

@Preview(showBackground = true)
@Composable
fun CheckBoxWithTextPreviewTrue() {
    CheckBoxWithText(
        label = "Some text for checkBox",
        uiState = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CheckBoxWithTextPreviewFalse() {
    CheckBoxWithText(
        label = "Some text for checkBox",
        uiState = false,
        onClick = {}
    )
}

fun onIndeterminateClick(value: Boolean?): Boolean =
    value?.let{!it}?: true