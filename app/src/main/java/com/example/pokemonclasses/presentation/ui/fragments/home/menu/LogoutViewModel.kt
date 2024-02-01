package com.example.pokemonclasses.presentation.ui.fragments.home.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.repository.UserRepository
import com.example.pokemonclasses.presentation.ui.viewmodel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class LogoutUiModel(
    val navigateToLogin: Event<Unit?>? = null,
)

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableLiveData<LogoutUiModel> = MutableLiveData()
    val uiState: LiveData<LogoutUiModel>
        get() = _uiState

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setUserUnLogged()
            emitUiModel(navigateToLogin = Unit)
        }
    }

    private suspend fun emitUiModel(
        navigateToLogin: Unit? = null,
    ) {
        withContext(Dispatchers.Main) {
            _uiState.value = LogoutUiModel(
                navigateToLogin = Event(navigateToLogin),
            )
        }
    }
}
