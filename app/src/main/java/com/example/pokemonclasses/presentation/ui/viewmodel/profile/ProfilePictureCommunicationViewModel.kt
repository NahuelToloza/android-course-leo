package com.example.pokemonclasses.presentation.ui.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonclasses.presentation.ui.viewmodel.Event
import javax.inject.Inject

class ProfilePictureCommunicationViewModel @Inject constructor() : ViewModel() {

    private val _clickCamera: MutableLiveData<Event<Unit>> = MutableLiveData()
    val clickCamera: LiveData<Event<Unit>>
        get() = _clickCamera

    private val _clickGallery: MutableLiveData<Event<Unit>> = MutableLiveData()
    val clickGallery: LiveData<Event<Unit>>
        get() = _clickGallery

    fun onClickCamera() {
        _clickCamera.value = Event(Unit)
    }

    fun onClickGallery() {
        _clickGallery.value = Event(Unit)
    }
}