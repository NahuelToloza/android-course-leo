package com.example.pokemonclasses.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.pokemonclasses.presentation.persistence.room.AppDatabase
import com.example.pokemonclasses.presentation.persistence.room.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideObjectDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}