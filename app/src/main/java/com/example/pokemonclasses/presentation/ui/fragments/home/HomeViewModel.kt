package com.example.pokemonclasses.presentation.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.Pokemon
import com.example.pokemonclasses.data.repository.PokemonRepository
import com.example.pokemonclasses.data.service.ResultWrapper
import com.example.pokemonclasses.presentation.ui.viewmodel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<HomeUiModel> = MutableLiveData()
    val uiState: LiveData<HomeUiModel>
        get() = _uiState

    fun getAllPokemons() = viewModelScope.launch(Dispatchers.Default) {
        when(val response = pokemonRepository.getAllPokemons()) {
            is ResultWrapper.Success -> {
                val listPokemonItem = response.value.results.map {
                    Pokemon("", it.name ?: "")
                }
                withContext(Dispatchers.Main) {
                    emitUiModel(showPokemonList = listPokemonItem)
                }
            }
            is ResultWrapper.Error -> {
                emitUiModel(showError = response)
            }
        }
    }

    private suspend fun emitUiModel(
        showPokemonList: List<PokemonItem>? = null,
        showError: ResultWrapper.Error? = null,
    ) {
        withContext(Dispatchers.Main) {
            _uiState.value = HomeUiModel(
                showPokemonList = Event(showPokemonList),
                showError = Event(showError),
            )
        }
    }
}

data class HomeUiModel(
    val showPokemonList: Event<List<PokemonItem>?>? = null,
    val showError: Event<ResultWrapper.Error?>? = null,
)