package com.example.validation_etude.ui.form

import com.example.validation_etude.ui.reusable.utils.AbstractViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FormViewModel(): AbstractViewModel<FormState, FormEvent>() {
    override val _stateFlow: MutableStateFlow<FormState> = MutableStateFlow(FormState.Main())
    override val stateFlow: StateFlow<FormState> = _stateFlow.asStateFlow()

    override fun onEvent(e: FormEvent) {
        when (e) {
            is FormEvent.OnActivityChanged -> TODO()
            is FormEvent.OnIsCleverChanged -> TODO()
            is FormEvent.OnNameChanged -> TODO()
            FormEvent.OnSave -> TODO()
        }
    }
}