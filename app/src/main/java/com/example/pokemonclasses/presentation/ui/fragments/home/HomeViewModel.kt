package com.example.pokemonclasses.presentation.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.Pokemon
import com.example.pokemonclasses.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonsLiveData: MutableLiveData<List<PokemonItem>> = MutableLiveData()
    val pokemonsLiveData: LiveData<List<PokemonItem>>
        get() = _pokemonsLiveData

    fun getAllPokemons() = viewModelScope.launch(Dispatchers.Default) {
        val listPokemonItem = pokemonRepository.getAllPokemons().results.map {
            Pokemon("", it.name ?: "")
        }
        withContext(Dispatchers.Main) {
            _pokemonsLiveData.value = listPokemonItem
        }
    }
}