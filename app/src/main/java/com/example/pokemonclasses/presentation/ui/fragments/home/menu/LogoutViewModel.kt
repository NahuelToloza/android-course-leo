package com.example.pokemonclasses.presentation.ui.fragments.home.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    fun updateData(){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.setUserUnLogged()
        }
    }
}