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
    modifier: Modifier
) {
    OutlinedCardWithTitle(
        title = UiText.StringRes(R.string.mind).asString(),
        modifier = modifier.fillMaxWidth(),
        content = { interactionSource, onContentClick ->
            CleverContent(
                isClever = isClever,
                onIsCleverChanged = onIsCleverChanged,
                activity = activity,
                onActivityChanged = onActivityChanged,
                modifier = modifier,
                interactionSource = interactionSource,
                onContentClickFocusManage = onContentClick
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
) {
    Column{
        isClever(
            isClever = isClever,
            onIsCleverChanged = onIsCleverChanged,
            onClickFocusManage = onContentClickFocusManage,
            modifier = Modifier
        )
        Activity(
            activity = activity,
            onActivityChanged = onActivityChanged,
            interactionSource = interactionSource,
            modifier = Modifier
        )
    }
}

@Composable
fun Activity(
    activity: String,
    onActivityChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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
        interactionSource = interactionSource
    )
}

@Composable
fun isClever(
    isClever: Boolean?,
    onIsCleverChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClickFocusManage: () -> Unit = {},
    ) {
        CheckBoxWithText(
            label = UiText.StringRes(R.string.clever).asString(),
            uiState = isClever,
            onClick = {
                onClickFocusManage()
                val newIsClever = onIndeterminateClick(isClever)
                onIsCleverChanged(newIsClever)
            })
}

@Composable
fun NameBlock(
    name: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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
        interactionSource = interactionSource
    )
}