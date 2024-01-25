package com.example.pokemonclasses.data.service

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PokemonApi @Inject constructor(private val apiService: PokemonApiService) {
    suspend fun getAllPokemons() = safeApiCall(Dispatchers.IO) {
        apiService.getAllPokemons()
    }
}