package com.example.pokemonclasses.domain.repository

import android.content.Context
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.presentation.persistence.room.daos.UserDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val userDao: UserDao,
) {
    fun saveUser(user: User) {
        userDao.insertUser(user.toUserEntity())
    }

    fun getAllUser(): List<User> {
        return userDao.getAllUsers().map {
            it.toUser()
        }
    }

    fun getUser(email: String): User? {
        return userDao.getUser(email)
    }
}