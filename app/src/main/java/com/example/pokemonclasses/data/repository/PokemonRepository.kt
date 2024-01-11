package com.example.pokemonclasses.data.repository

import com.example.pokemonclasses.data.service.PokemonApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokemonApi
) {
    suspend fun getAllPokemons() = api.getAllPokemons()
}