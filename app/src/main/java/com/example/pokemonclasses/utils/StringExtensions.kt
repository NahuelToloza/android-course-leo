package com.example.pokemonclasses.utils

import android.util.Patterns
import java.util.regex.Pattern

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isValidPassword(): Boolean {
    val pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher = pattern.matcher(this.toString())
    return matcher.matches()
}

private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$"