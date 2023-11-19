package com.example.validation_etude.ui.form

import androidx.compose.runtime.Immutable
import com.example.validation_etude.ui.reusable.utils.Result
import com.example.validation_etude.ui.reusable.utils.UiEvent
import com.example.validation_etude.ui.reusable.utils.UiState
import com.example.validation_etude.ui.reusable.utils.UiText
import com.example.validation_etude.ui.reusable.utils.validateName

@Immutable
sealed interface FormState: UiState {
    data class Main(
        val name: String = "",
        val isClever: Boolean? = null,
        val activity: String = "",
    ): FormState {
        val isValid = listOf(
            name.validateName() is Result.Ok,
            isClever != null,
            activity.validateName() is Result.Ok
        ).all { it }
    }
    data class Error(val error: UiText): FormState
}

@Immutable
sealed interface FormEvent: UiEvent {
    data class OnNameChanged(val name: String): FormEvent
    data class OnIsCleverChanged(val isClever: Boolean): FormEvent
    data class OnActivityChanged(val activity: String): FormEvent
    object OnSave: FormEvent
}