package com.example.pokemonclasses.data.repository

import android.content.Context
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.presentation.persistence.SharedPreferencesManager
import com.example.pokemonclasses.presentation.persistence.room.daos.UserDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val userDao: UserDao,
    private val sharedPreferencesManager: SharedPreferencesManager,
) {
    fun saveUser(user: User) {
        userDao.insertUser(user.toUserEntity())
    }

    fun getAllUser(): List<User> {
        return userDao.getAllUsers().map {
            it.toUser()
        }
    }

    fun getUser(email: String?): User? {
        return userDao.getUser(email)?.toUser()
    }

    fun isUserLogged() = sharedPreferencesManager.isUserLogged()

    fun setUserUnLogged()= sharedPreferencesManager.setUserUnLogged()

    fun getLoggedEmail() = sharedPreferencesManager.getAppData().loggedEmail

    fun setUserLogged(email: String) = sharedPreferencesManager.setUserLogged(email)

}