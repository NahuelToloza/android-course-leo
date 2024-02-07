package com.example.pokemonclasses.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokemonclasses.databinding.BottomFragmentProfilePictureBinding
import com.example.pokemonclasses.presentation.ui.viewmodel.profile.ProfilePictureCommunicationViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfilePictureBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private var _binding: BottomFragmentProfilePictureBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfilePictureCommunicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomFragmentProfilePictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCamera.setOnClickListener {
            viewModel.onClickCamera()
            dismissAllowingStateLoss()
        }
        binding.tvGallery.setOnClickListener {
            viewModel.onClickGallery()
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}