package com.example.validation_etude.domain

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern

interface Name: Validatable{
    override val value: String
}

interface Validatable {
    val value: String
}
fun Validatable.validate(minLen: Int = 0, maxLen: Int = 50, validCharsRegex: Regex): Validatable {
    Validation<Validatable>{
        Validatable::value {
            minLength(minLen) hint "${this.javaClass.simpleName} should be at least $minLen chars"
            maxLength(maxLen) hint "${this.javaClass.simpleName} should be no more than $maxLen chars"
            pattern(validCharsRegex) hint "Allowed characters are: $validCharsRegex"
        }
    }.validateAndThrowOnFailure(this)
    return this
}

fun <T> Validation<T>.validateAndThrowOnFailure(value: T) {
    val result = validate(value)
    if (result is Invalid<T>) {
        throw IllegalArgumentException(result.errors.toString())
    }
}

@JvmInline
value class IndividualName private constructor(override val value: String): Name {
    companion object {
        private const val NAME_MIN_LENGTH = 2
        private const val NAME_MAX_LENGTH = 23
        private val NAME_VALID_CHARS = "[A-Za-zА-Яа-я\\-]+".toRegex()
        operator fun invoke(name: String): IndividualName = IndividualName(name.trim())
            .validate(
                NAME_MIN_LENGTH,
                NAME_MAX_LENGTH,
                NAME_VALID_CHARS
            ) as IndividualName
    }
}

