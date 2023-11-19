package com.example.validation_etude.ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.validation_etude.ui.form.components.CleverBlock
import com.example.validation_etude.ui.form.components.NameBlock
import com.example.validation_etude.ui.reusable.components.FabState
import com.example.validation_etude.ui.reusable.components.LocalSetFabState
import com.example.validation_etude.ui.reusable.utils.IconSetup
import com.example.validation_etude.ui.reusable.utils.UiText

@Composable
fun FormScreen(modifier: Modifier = Modifier) {
    val viewmodel: FormViewModel = viewModel()
    val uiState by viewmodel.stateFlow.collectAsStateWithLifecycle()

    //ToDo SnackBarAndAlertDialogWatcher(snackbarNotice)
    //SnackBar Block
    val snackbarHostState = LocalSnackbarHostState.current
    val snackbarNotice by viewmodel.snackbarText.collectAsStateWithLifecycle()
    val snackbarText = snackbarNotice.deliver()?.let{ if (it > -1) stringResource(id = it) else "" } ?: ""
    LaunchedEffect(snackbarText) {
        if (snackbarText.isNotBlank()) snackbarHostState.showSnackbar(snackbarText)
    }
    val setFabState = LocalSetFabState.current
    LaunchedEffect(uiState) {
        if (uiState is FormState.Main) setFabState(
            FabState(
                onClick = {
                    viewmodel.onEvent(
                        FormEvent.OnSave
                    )
                },
                icon = IconSetup(
                    imageVector = Icons.Default.SaveAlt,
                    contentDescription = UiText.String("save") //ToDo Strings!
                ),
                isVisible = true
            )
        )
    }
    when (uiState) {
        is FormState.Error -> viewmodel.error()
        is FormState.Main -> FormScreen(uiState = uiState as FormState.Main, onEvent = viewmodel::onEvent)
    }

}

@Composable
fun FormScreen(
    uiState: FormState.Main,
    onEvent: (FormEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    FormScreen(
        name = uiState.name,
        onNameChanged = { onEvent(FormEvent.OnNameChanged(it)) },
        isClever = uiState.isClever,
        onIsCleverChanged = { onEvent(FormEvent.OnIsCleverChanged(it)) },
        activity = uiState.activity,
        onActivityChanged = { onEvent(FormEvent.OnActivityChanged(it)) },
        modifier = modifier
    )
}

@Composable
fun FormScreen(
    name: String,
    onNameChanged: (String) -> Unit,
    isClever: Boolean?,
    onIsCleverChanged: (Boolean) -> Unit,
    activity: String,
    onActivityChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState()) //ToDo Maybe Topappbar scrollable
    ) {
        NameBlock(
            name = name,
            onNameChanged = onNameChanged,
            modifier = Modifier
        )
        CleverBlock(
            isClever = isClever,
            onIsCleverChanged = onIsCleverChanged,
            activity = activity,
            onActivityChanged = onActivityChanged,
            modifier = modifier
        )
    }
}

