package com.example.validation_etude.ui.reusable.components

import androidx.compose.runtime.compositionLocalOf
import com.example.validation_etude.ui.reusable.utils.IconSetup

data class FabState(val onClick: () -> Unit?, val icon: IconSetup?, val isVisible: Boolean)

//inspired by https://stackoverflow.com/questions/71186641/how-to-control-a-scaffolds-floatingactionbutton-onclick-from-child-composable
val LocalSetFabState = compositionLocalOf<(FabState) -> Unit> { error("No setFabState provided") }