package com.example.validation_etude.ui.reusable.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

//taken from https://stackoverflow.com/questions/74044246/how-to-get-stringresource-if-not-in-a-composable-function
/*
Inside your ViewModel where you can simply supply the id of the string resource

var stringResourceVariable = UiText.StringResource(R.string.some_string_resource)
Then in your Composable

viewModel.stringResourceVariable.asString()
you can simply invoke asString and it will be resolved depending on what type of UiText string you supplied.

The benefit of having this approach, is you won't have any problems supplying string resources in your ViewModel.
 */
sealed interface UiText {

    data class String(val value: kotlin.String) : UiText

    class StringRes(
        @androidx.annotation.StringRes val resourceId: Int,
        vararg val args: Any
    ) : UiText

    class PluralRes(
        @androidx.annotation.PluralsRes val resourceId: Int,
        val count: Int,
        vararg val args: Any
    ): UiText
    @Composable
    fun asString(): kotlin.String {
        return when (this) {
            is String -> {
                value
            }

            is StringRes -> {
                stringResource(
                    id = resourceId,
                    formatArgs = args
                )
            }

            is PluralRes -> {
                pluralStringResource(
                    id = resourceId,
                    count = count,
                    formatArgs = args
                )
            }
        }
    }
}