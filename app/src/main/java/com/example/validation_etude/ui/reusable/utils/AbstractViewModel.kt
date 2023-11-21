package com.example.validation_etude.ui.reusable.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.validation_etude.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class AbstractViewModel <S: UiState, E: UiEvent>: ViewModel() {

    protected val _snackbarText = MutableStateFlow(Notice(-1))
    val snackbarText: StateFlow<Notice<Int>> = _snackbarText.asStateFlow()

    protected abstract val _stateFlow: MutableStateFlow<S>
    abstract val stateFlow: StateFlow<S>

    open protected var state: S
        get() = _stateFlow.value
        set(newState) {
            viewModelScope.launch { _stateFlow.update { newState }}
        }

    abstract fun onEvent(e: E)

    fun error() {
        _snackbarText.value = Notice(R.string.smth_get_wrong) //ToDo Refactor to AlertDialog
    }
}

