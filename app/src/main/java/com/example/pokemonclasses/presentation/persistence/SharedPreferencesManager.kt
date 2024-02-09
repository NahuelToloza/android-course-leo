package com.example.pokemonclasses.presentation.persistence

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun isUserLogged() = getAppData().loggedEmail != null

    fun setUserLogged(email: String) {
        setAppData(email)
    }

    fun setUserUnLogged(){
        setAppData(null)
    }

    fun getAppData(): AppData {
        val isUserLogged = sharedPreferences.getString(LOGGED_EMAIL, null)
        return AppData(isUserLogged)
    }

    private fun setAppData(loggedEmail: String?) {
        sharedPreferences.edit {
            putString(LOGGED_EMAIL, loggedEmail)
            apply()
        }
    }

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val LOGGED_EMAIL = "LOGGED_EMAIL"
    }
}