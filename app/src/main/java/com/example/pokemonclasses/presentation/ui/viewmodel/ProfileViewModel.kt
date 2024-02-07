package com.example.pokemonclasses.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonclasses.R
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
        val user = userRepository.getUser(email)
        emitUiModel(setupViews = SetupView(email, user?.profilePicture))
    }

    fun profilePictureWasTaken(uri: Uri?) = uri?.let {
        viewModelScope.launch(Dispatchers.IO) {
            val userLoggedEmail =  userRepository.getLoggedEmail()
            val user = userRepository.getUser(userLoggedEmail)
            user?.let {
                val newUser = user.copy(profilePicture = uri)
                userRepository.saveUser(newUser)
                emitUiModel(showMessage = R.string.profile_picture_saved)
            } ?: run {
                emitUiModel(showMessage = R.string.error_user_not_found)
            }
        }
    }

    private suspend fun emitUiModel(
        setupViews: SetupView? = null,
        showMessage: Int? = null,
    ) = withContext(Dispatchers.Main) {
        _uiState.value = ProfileUiModel(
            setupViews = Event(setupViews),
            showMessage = Event(showMessage),
        )
    }
}

data class ProfileUiModel(
    val setupViews: Event<SetupView?>? = null,
    val showMessage: Event<Int?>? = null,
)

data class SetupView(
    val email: String?,
    val profilePicture: Uri?,
)