package com.example.validation_etude.ui.form

import android.util.Log
import com.example.validation_etude.R
import com.example.validation_etude.ui.reusable.utils.AbstractViewModel
import com.example.validation_etude.ui.reusable.utils.Notice
import com.example.validation_etude.ui.reusable.utils.Result
import com.example.validation_etude.ui.reusable.utils.validateName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "Form"
class FormViewModel(): AbstractViewModel<FormState, FormEvent>() {
    override val _stateFlow: MutableStateFlow<FormState> = MutableStateFlow(FormState.Main())
    override val stateFlow: StateFlow<FormState> = _stateFlow.asStateFlow()

    override fun onEvent(e: FormEvent) {
        when (e) {
            is FormEvent.OnActivityChanged -> state = (state as FormState.Main).copy(activity = e.activity, focused = FocusedState(3))
            is FormEvent.OnIsCleverChanged -> state = (state as FormState.Main).copy(isClever = e.isClever, focused = FocusedState(3))
            is FormEvent.OnNameChanged -> state = (state as FormState.Main).copy(name = e.name, focused = FocusedState(3))
            FormEvent.OnSave -> {
                if ((state as FormState.Main).isValid) {
                    _snackbarText.value = Notice(R.string.recorded)
                    state = FormState.Main()
                } else
                with(state as FormState.Main) {
                    state = when {
                        name.validateName() is Result.Fail -> {
                            _snackbarText.value = Notice(R.string.name_error)
                            copy(focused = FocusedState(3, 0))
                        }
                        isClever == null -> {
                            _snackbarText.value = Notice(R.string.is_clever_error)
                            Log.d(TAG, "onEvent: isClever = null $isClever")
                            copy(focused = FocusedState(3, 1))
                        }
                        activity.validateName() is Result.Fail -> {
                            _snackbarText.value = Notice(R.string.activity_error)
                            copy(focused = FocusedState(3, 2))
                        }
                        else -> this
                    }
                }
            }
        }
    }
}