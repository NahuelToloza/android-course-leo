package com.example.pokemonclasses.data.service

import javax.inject.Inject

class PokemonApi @Inject constructor(private val apiService: PokemonApiService) {
    suspend fun getAllPokemons() = apiService.getAllPokemons()
}