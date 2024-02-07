package com.example.pokemonclasses.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _navigateToProfile: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToProfile: LiveData<Event<Unit>>
        get() = _navigateToProfile

    private val _showDrawerData: MutableLiveData<Event<Uri?>> = MutableLiveData()
    val showDrawerData: LiveData<Event<Uri?>>
        get() = _showDrawerData

    fun onProfileImageClicked() {
        _navigateToProfile.value = Event(Unit)
    }

    fun getDrawerData() = viewModelScope.launch(Dispatchers.IO) {
        val email = userRepository.getLoggedEmail()
        val user = userRepository.getUser(email)
        withContext(Dispatchers.Main) {
            _showDrawerData.value = Event(user?.profilePicture)
        }
    }

}