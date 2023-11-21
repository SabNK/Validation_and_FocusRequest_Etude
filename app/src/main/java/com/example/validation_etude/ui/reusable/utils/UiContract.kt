package com.example.validation_etude.ui.reusable.utils

import androidx.compose.runtime.Immutable

@Immutable
interface UiEvent

@Immutable
interface UiState

@Immutable
data class FocusedState (val size: Int, val focusedIndex: Int? = null) {
    init {
        require (size in 1..99 ) {"Not valid elements number"}
        require( focusedIndex?.let{ it in 0 until size }?:true ) {"focused index should be within total counts"}
    }
    val areFocused: List<Boolean> = (0 until size).map{ it == focusedIndex }
    val notFocused = focusedIndex == null
    val hasFocus = focusedIndex != null
    fun toChild(from: Int, to: Int): FocusedState {
        require (from < to) { "child list should contains at least one element"  }
        require ( to - from < size) { "child list should be smaller than parent" }
        return FocusedState(
            to - from,
            focusedIndex?.let { if (focusedIndex in from until to) focusedIndex - from else null })
    }
    val first = focusedIndex?.let{it == 0} ?: false
}