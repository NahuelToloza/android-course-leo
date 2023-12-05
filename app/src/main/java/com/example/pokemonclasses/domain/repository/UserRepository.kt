package com.example.pokemonclasses.domain.repository

import androidx.fragment.app.FragmentActivity
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.presentation.persistence.room.AppDatabase

class UserRepository(activity: FragmentActivity) {
    private val dao = AppDatabase.getInstance(activity.applicationContext).userDao()
    fun saveUser(user: User) {
        dao.insertUser(user.toUserEntity())
    }

    fun getAllUser(): List<User> {
        return dao.getAllUsers().map {
            it.toUser()
        }
    }

    fun getUser(email: String): User? {
        return dao.getUser(email)
    }
}