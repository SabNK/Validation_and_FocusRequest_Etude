package com.example.validation_etude.ui.reusable.utils

import com.example.validation_etude.R
import com.example.validation_etude.domain.IndividualName


private const val TAG = "EditPole"



fun String.validateName(): Result<Nothing, Notice<Int>> =
    try {
        IndividualName(this)
        Result.Ok()
    } catch (e: IllegalArgumentException) {
        e.message!!.run {
            when {
                contains("should be at least") -> Result.Fail(Notice(R.string.not_enough_chars))
                contains("should be no more") -> Result.Fail(Notice(R.string.is_too_long))
                contains("Allowed characters are") -> Result.Fail(Notice(R.string.not_allowed_chars))
                else -> throw InternalError("unexpected validation error")
            }
        }
    }

