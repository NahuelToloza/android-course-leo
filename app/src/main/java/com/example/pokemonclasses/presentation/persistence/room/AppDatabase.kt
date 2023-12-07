package com.example.pokemonclasses.presentation.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemonclasses.presentation.persistence.room.daos.UserDao
import com.example.pokemonclasses.presentation.persistence.room.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}