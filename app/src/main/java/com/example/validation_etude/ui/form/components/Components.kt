package com.example.validation_etude.ui.form.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.validation_etude.R
import com.example.validation_etude.ui.form.FocusedState
import com.example.validation_etude.ui.reusable.components.ValidatedOutlinedTextField
import com.example.validation_etude.ui.reusable.utils.UiText
import com.example.validation_etude.ui.reusable.utils.validateName
import ru.polescanner.droidmvp.ui.reusablecomponents.CheckBoxWithText
import ru.polescanner.droidmvp.ui.reusablecomponents.OutlinedCardWithTitle
import ru.polescanner.droidmvp.ui.reusablecomponents.onIndeterminateClick

@Composable
fun CleverBlock(
    isClever: Boolean?,
    onIsCleverChanged: (Boolean) -> Unit,
    activity: String,
    onActivityChanged: (String) -> Unit,
    modifier: Modifier,
    focused: FocusedState
) {
    OutlinedCardWithTitle(
        title = UiText.StringRes(R.string.mind).asString(),
        modifier = modifier.fillMaxWidth(),
        content = { interactionSource, onContentClick, _, modifier ->
            CleverContent(
                isClever = isClever,
                onIsCleverChanged = onIsCleverChanged,
                activity = activity,
                onActivityChanged = onActivityChanged,
                modifier = modifier,
                interactionSource = interactionSource,
                onContentClickFocusManage = onContentClick,
                focused = focused
            )
        }
    )


}

@Composable
fun CleverContent(
    isClever: Boolean?,
    onIsCleverChanged: (Boolean) -> Unit,
    activity: String,
    onActivityChanged: (String) -> Unit,
    modifier: Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onContentClickFocusManage: () -> Unit = {},
    focused: FocusedState = FocusedState(2)
) {
    Column{
        isClever(
            isClever = isClever,
            onIsCleverChanged = onIsCleverChanged,
            onClickFocusManage = onContentClickFocusManage,
            modifier = Modifier,
            focused = focused.toChild(0,1)
        )
        Activity(
            activity = activity,
            onActivityChanged = onActivityChanged,
            interactionSource = interactionSource,
            modifier = Modifier,
            focused = focused.toChild(1,2)
        )
    }
}

@Composable
fun Activity(
    activity: String,
    onActivityChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: FocusedState = FocusedState(1)
) {
    var isError by remember { mutableStateOf(false) }
    ValidatedOutlinedTextField(
        text = activity,
        onValid = onActivityChanged,
        validator = String::validateName,
        isError = isError,
        setError = { isError = it },
        labelRes = R.string.activity,
        placeholderRes = R.string.activity,
        modifier = modifier,
        interactionSource = interactionSource,
        focused = focused.first
    )
}

@Composable
fun isClever(
    isClever: Boolean?,
    onIsCleverChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClickFocusManage: () -> Unit = {},
    focused: FocusedState = FocusedState(1)
    ) {
        CheckBoxWithText(
            label = UiText.StringRes(R.string.clever).asString(),
            uiState = isClever,
            onClick = {
                onClickFocusManage()
                val newIsClever = onIndeterminateClick(isClever)
                onIsCleverChanged(newIsClever)
            },
            focused = focused.first)
}

@Composable
fun NameBlock(
    name: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: FocusedState = FocusedState(1)
) {
    var isError by remember { mutableStateOf(false) }
    ValidatedOutlinedTextField(
        text = name,
        onValid = onNameChanged,
        validator = String::validateName,
        isError = isError,
        setError = { isError = it },
        labelRes = R.string.name,
        placeholderRes = R.string.name,
        modifier = modifier,
        interactionSource = interactionSource,
        focused = focused.first
    )
}