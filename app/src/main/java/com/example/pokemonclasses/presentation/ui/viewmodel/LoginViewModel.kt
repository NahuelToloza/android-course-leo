package com.example.pokemonclasses.presentation.ui.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.data.User
import com.example.pokemonclasses.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LoginUiModel(
    val navigateToHome: Event<User?>? = null,
    val showErrorInvalidUser: Event<Unit?>? = null,
)

class LoginViewModel: ViewModel() {

    private val _uiState: MutableLiveData<LoginUiModel> = MutableLiveData()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    fun loginButtonClicked(email: String, password: String, activity: FragmentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedUser = UserRepository(activity).getUser(email)
            if (savedUser != null && savedUser.email == email && savedUser.password == password) {
                val user = User(email, password)
                updateUiModel(navigateToHome = user)
            } else {
                updateUiModel(showErrorInvalidUser = Unit)
            }
        }
    }

    private suspend fun updateUiModel(
        navigateToHome: User? = null,
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