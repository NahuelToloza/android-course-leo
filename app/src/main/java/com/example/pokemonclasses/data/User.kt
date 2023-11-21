package com.example.pokemonclasses.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val age: Int,
): Parcelable