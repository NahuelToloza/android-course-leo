package com.example.pokemonclasses.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val password: String,
    //@DrawableRes val image: Int? = null,
): Parcelable