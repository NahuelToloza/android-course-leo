package com.example.pokemonclasses.data

import com.example.pokemonclasses.presentation.ui.fragments.home.PokemonItem
import com.example.pokemonclasses.presentation.ui.fragments.home.PokemonListAdapter.Companion.POKEMON_TYPE

data class Pokemon(
    val image : String,
    val name : String
): PokemonItem {
    override fun getViewType() = POKEMON_TYPE

    override fun getId() = name

    override fun getContent() = name + image
}
