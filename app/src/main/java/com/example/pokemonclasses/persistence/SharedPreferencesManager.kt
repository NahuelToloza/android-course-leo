package com.example.pokemonclasses.persistence

import android.app.Activity
import android.content.Context
import androidx.core.content.edit
import com.example.pokemonclasses.data.User

class SharedPreferencesManager {
    fun saveUser(activity: Activity, user: User) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        sharedPref.edit {
            putString(EMAIL, user.email)
            putString(PASSWORD, user.password)
            apply()
        }
    }

    fun getUser(activity: Activity): User {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return User(
            email = sharedPref.getString(EMAIL, "") ?: "",
            password = sharedPref.getString(PASSWORD, "") ?: "",
        )
    }

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
}