package com.example.pokemonclasses.presentation.persistence.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemonclasses.data.User

@Entity
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
) {
    fun toUser() = User(
        email = email,
        password = password
    )
}