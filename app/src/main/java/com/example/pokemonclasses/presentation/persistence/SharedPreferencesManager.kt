package com.example.pokemonclasses.presentation.persistence

import android.app.Activity
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.pokemonclasses.data.User
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun isUserLogged() = getAppData().isUserLogged

    fun setUserLogged() {
        setAppData(true)
    }

    fun saveUser(activity: Activity, user: User) {
        sharedPreferences.edit {
            putString(EMAIL, user.email)
            putString(PASSWORD, user.password)
            apply()
        }
    }

    fun getUser(activity: Activity): User {
        return User(
            email = sharedPreferences.getString(EMAIL, "") ?: "",
            password = sharedPreferences.getString(PASSWORD, "") ?: "",
        )
    }

    private fun getAppData(): AppData {
        val isUserLogged = sharedPreferences.getBoolean(IS_USER_LOGGED, false)
        return AppData(isUserLogged)
    }

    private fun setAppData(isUserLogged: Boolean) {
        sharedPreferences.edit {
            putBoolean(IS_USER_LOGGED, isUserLogged)
            apply()
        }
    }

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val IS_USER_LOGGED = "IS_USER_LOGGED"
    }
}