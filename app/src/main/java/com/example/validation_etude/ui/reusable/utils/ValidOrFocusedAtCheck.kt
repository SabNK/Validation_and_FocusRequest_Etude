package com.example.validation_etude.ui.reusable.utils


typealias Focusable<E> = ValidOrFocusedAtCheck<E>

data class ValidOrFocusedAtCheck<E> (
    val value: E,
    val isFocused: Boolean
)

/*
fun String.toFocusable() = Focusable<String>(this, false)
fun Int.toFocusable() = Focusable<Int>(this, false)
fun Nullable<E>.toFocusable() = Focusable<E>(this, false)
*/
fun <T: Any?> T?.toNullableFocusable(): ValidOrFocusedAtCheck<T?> =
    this?.let{ValidOrFocusedAtCheck<T?>(it, false)} ?: ValidOrFocusedAtCheck<T?>(null, false)

fun <T: Any> T.toFocusable(): Focusable<T> = Focusable<T>(this, false)

data class ScreenStateMain (
    val name: ValidOrFocusedAtCheck<String> = "".toFocusable(),
    val lastName: ValidOrFocusedAtCheck<String> = "".toFocusable(),
    val isClever: ValidOrFocusedAtCheck<Boolean?> = null.toNullableFocusable(),
    val activity: ValidOrFocusedAtCheck<String> = "".toFocusable()
) {
    init{
        require(
            oneOrNoFocused(
                name,
                lastName,
                isClever,
                activity
            )
        ) {"More than one element is focused"}
    }

    fun oneOrNoFocused(vararg elements: ValidOrFocusedAtCheck<out Any?>): Boolean =
        elements.count{it.isFocused} <= 1


}