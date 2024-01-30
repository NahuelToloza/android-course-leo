package com.example.pokemonclasses.presentation.ui.viewmodel

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
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState: MutableLiveData<ProfileUiModel> = MutableLiveData()
    val uiState: LiveData<ProfileUiModel>
        get() = _uiState

    fun getProfileData() = viewModelScope.launch(Dispatchers.IO) {
        val email = userRepository.getLoggedEmail()
        emitUiModel(setupViews = email)
    }

    private suspend fun emitUiModel(
        setupViews: String? = null
    ) = withContext(Dispatchers.Main) {
        _uiState.value = ProfileUiModel(setupViews = Event(setupViews))
    }
}

data class ProfileUiModel(
    val setupViews: Event<String?>? = null
)