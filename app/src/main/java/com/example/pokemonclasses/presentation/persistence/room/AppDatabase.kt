package com.example.pokemonclasses.presentation.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonclasses.presentation.persistence.room.daos.UserDao
import com.example.pokemonclasses.presentation.persistence.room.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}