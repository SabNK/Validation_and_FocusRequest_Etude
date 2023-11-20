package com.example.validation_etude.ui.form

import androidx.compose.runtime.Immutable
import com.example.validation_etude.ui.reusable.utils.Result
import com.example.validation_etude.ui.reusable.utils.UiEvent
import com.example.validation_etude.ui.reusable.utils.UiState
import com.example.validation_etude.ui.reusable.utils.UiText
import com.example.validation_etude.ui.reusable.utils.validateName

private const val TAG = "Focused"
@Immutable
sealed interface FormState: UiState {
    data class Main(
        val name: String = "",
        val isClever: Boolean? = null,
        val activity: String = "",
        val focused: FocusedState = FocusedState(3)
    ): FormState {
        val isValid = listOf(
            name.validateName() is Result.Ok,
            isClever != null,
            activity.validateName() is Result.Ok
        ).all { it }
    }
    data class Error(val error: UiText): FormState
}

data class FocusedState (val size: Int, val focusedIndex: Int? = null) {
    init {
        require (size in 1..99 ) {"Not valid elements number"}
        require( focusedIndex?.let{ it in 0 until size }?:true ) {"focused index should be within total counts"}
    }
    val isFocused: List<Boolean> = (0 until size).map{ it == focusedIndex }
    val notFocused = focusedIndex == null
    fun toChild(from: Int, to: Int): FocusedState {
        //Log.d(TAG, "toChild: from $from , to $to , focusedIndex $focusedIndex ")
        //Log.d(TAG, "toChild: new ${focusedIndex?.let{ if (focusedIndex in from..to) (focusedIndex - from) else null }}")
        require (from < to) { "child list should contains at least one element"  }
        require ( to - from < size) { "child list should be smaller than parent" }
        return FocusedState(to-from, focusedIndex?.let{ if (focusedIndex in from until to) focusedIndex - from else null })
    }
    val first = focusedIndex?.let{it == 0} ?: false
}

@Immutable
sealed interface FormEvent: UiEvent {
    data class OnNameChanged(val name: String): FormEvent
    data class OnIsCleverChanged(val isClever: Boolean): FormEvent
    data class OnActivityChanged(val activity: String): FormEvent
    object OnSave: FormEvent
}