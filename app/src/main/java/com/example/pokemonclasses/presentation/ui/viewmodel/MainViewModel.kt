package com.example.pokemonclasses.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _navigateToProfile: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToProfile: LiveData<Event<Unit>>
        get() = _navigateToProfile

    fun onProfileImageClicked() {
        _navigateToProfile.value = Event(Unit)
    }

}