package com.example.pokemonclasses.presentation.persistence.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonclasses.presentation.persistence.room.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userentity")
    fun getAllUsers(): List<UserEntity>

    @Query ("SELECT * FROM userentity WHERE email = :emailToFind LIMIT 1")
    fun getUser(emailToFind: String?): UserEntity?
}