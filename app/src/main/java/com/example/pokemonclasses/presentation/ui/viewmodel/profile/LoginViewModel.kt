package com.example.pokemonclasses.presentation.ui.viewmodel.profile

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

data class LoginUiModel(
    val navigateToHome: Event<Unit?>? = null,
    val showErrorInvalidUser: Event<Unit?>? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableLiveData<LoginUiModel> = MutableLiveData()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    fun setupData() = viewModelScope.launch(Dispatchers.Default) {
        if (userRepository.isUserLogged()) {
            emitUiModel(navigateToHome = Unit)
        }
    }

    fun loginButtonClicked(
        email: String, password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedUser = userRepository.getUser(email)
            if (savedUser != null && savedUser.email == email && savedUser.password == password) {
                userRepository.setUserLogged(email)
                emitUiModel(navigateToHome = Unit)
            } else {
                emitUiModel(showErrorInvalidUser = Unit)
            }
        }
    }

    private suspend fun emitUiModel(
        navigateToHome: Unit? = null,
        showErrorInvalidUser: Unit? = null,
    ) {
        withContext(Dispatchers.Main) {
            _uiState.value = LoginUiModel(
                navigateToHome = Event(navigateToHome),
                showErrorInvalidUser = Event(showErrorInvalidUser),
            )
        }
    }
}