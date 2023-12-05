package com.example.pokemonclasses.data

import android.os.Parcelable
import com.example.pokemonclasses.presentation.persistence.room.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val password: String,
) : Parcelable {
    fun toUserEntity() = UserEntity(
        email = email,
        password = password
    )
}