package com.example.pokemonclasses.data.service

import com.example.pokemonclasses.data.response.GetAllPokemonsResponse
import retrofit2.http.GET

interface PokemonApiService {
    @GET("pokemon/")
    suspend fun getAllPokemons(): GetAllPokemonsResponse
}