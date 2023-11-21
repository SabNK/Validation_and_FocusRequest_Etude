package com.example.validation_etude.ui.form

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.validation_etude.ui.reusable.components.FabState
import com.example.validation_etude.ui.reusable.components.LocalSetFabState
import com.example.validation_etude.ui.reusable.utils.visible

@Composable
fun FormRoute() {
    val snackbarHostState = remember { SnackbarHostState() }
    val (fabState, setFabState) = remember{ mutableStateOf<FabState?>(null) }
    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState,
        LocalSetFabState provides setFabState
    ) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(), //ToDo topBar
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { fabState?.onClick?.invoke() },
                    modifier = Modifier.visible(fabState?.isVisible?: false),
                ) {
                    fabState?.icon?.let {
                        Icon(
                            it.imageVector,
                            it.contentDescription.asString()
                        )
                    }
                }
            }
        ) { padding ->
            FormScreen(modifier = Modifier.padding(padding))
        }
    }
}

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }
