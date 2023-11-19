package com.example.validation_etude.ui.reusable.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.visible(visible: Boolean) = then(
    if (visible) Modifier
    else Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {}
    }
)